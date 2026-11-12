package com.whatsapp.scheduler.service;

import com.whatsapp.scheduler.model.ScheduledMessage;
import com.whatsapp.scheduler.model.ScheduledMessage.MessageStatus;
import com.whatsapp.scheduler.model.ScheduledMessage.RecurrenceType;
import com.whatsapp.scheduler.model.ScheduledMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageSchedulerServiceTest {

    @Mock
    private ScheduledMessageRepository messageRepository;

    @Mock
    private WhatsAppService whatsAppService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private MessageSchedulerService schedulerService;

    @Test
    void manualFallbackIsNotReportedAsDeliveredOrRetried() {
        ScheduledMessage message = pendingMessage();
        when(whatsAppService.sendMessage(message.getPhoneNumber(), message.getMessageContent()))
                .thenReturn(false);
        when(whatsAppService.generateWaLink(message.getPhoneNumber(), message.getMessageContent()))
                .thenReturn("https://wa.me/34600000000?text=hello");
        when(messageRepository.save(any(ScheduledMessage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        schedulerService.processMessage(message);

        assertEquals(MessageStatus.AWAITING_MANUAL, message.getStatus());
        assertNull(message.getSentAt());
        assertTrue(message.getErrorMessage().startsWith("FALLBACK_LINK:"));
        verify(whatsAppService, times(1))
                .sendMessage(message.getPhoneNumber(), message.getMessageContent());
    }

    @Test
    void sendNowDoesNotRetryAnAwaitingManualMessage() {
        ScheduledMessage message = pendingMessage();
        message.setStatus(MessageStatus.AWAITING_MANUAL);
        when(messageRepository.findById(7L)).thenReturn(Optional.of(message));

        ScheduledMessage result = schedulerService.sendNow(7L);

        assertSame(message, result);
        assertEquals(MessageStatus.AWAITING_MANUAL, result.getStatus());
        verify(whatsAppService, never()).sendMessage(anyString(), anyString());
    }

    @Test
    void deleteMessageDeletesAnExistingRecord() {
        when(messageRepository.existsById(7L)).thenReturn(true);

        assertTrue(schedulerService.deleteMessage(7L));

        verify(messageRepository).deleteById(7L);
    }

    @Test
    void deleteMessageReturnsFalseWhenRecordDoesNotExist() {
        when(messageRepository.existsById(404L)).thenReturn(false);

        assertFalse(schedulerService.deleteMessage(404L));

        verify(messageRepository, never()).deleteById(anyLong());
    }

    private ScheduledMessage pendingMessage() {
        ScheduledMessage message = new ScheduledMessage();
        message.setId(7L);
        message.setPhoneNumber("34600000000");
        message.setMessageContent("Hello");
        message.setScheduledAt(LocalDateTime.now().minusMinutes(1));
        message.setRecurrence(RecurrenceType.NONE);
        message.setStatus(MessageStatus.PENDING);
        return message;
    }
}
