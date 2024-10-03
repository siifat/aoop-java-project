package lms.controller.teacher;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.CreateChatInviteLink;
import org.telegram.telegrambots.meta.api.objects.ChatInviteLink;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Telegram Bot Class
public class TelegramBot extends TelegramLongPollingBot {

    private final String botToken;

    public TelegramBot(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return "sifatullahuiu_bot";  // Replace with your bot's username
    }

    // Method to create an invite link for a group
    public ChatInviteLink createInviteLink(String chatId) throws TelegramApiException {
        CreateChatInviteLink inviteLink = new CreateChatInviteLink();
        inviteLink.setChatId(chatId);  // The chat ID of the group (e.g., "-1234567890")
        inviteLink.setExpireDate(null);  // You can set an expiration time if needed
        inviteLink.setMemberLimit(10);   // Optional: Limit the number of users who can join via this link

        // Execute the invite link creation and return the result
        return execute(inviteLink);
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        // Handle updates here if needed (e.g., messages received by the bot)
    }
}
