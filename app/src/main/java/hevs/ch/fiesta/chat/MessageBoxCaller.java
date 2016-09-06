package hevs.ch.fiesta.chat;

import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.MessageBoxEntity;

/**
 * Created by Anthony on 06/09/2016.
 */
public interface MessageBoxCaller {
    void update(MessageBoxEntity messageBoxEntity);
    String getMessageBoxId();
}
