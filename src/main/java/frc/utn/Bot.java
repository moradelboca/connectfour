package frc.utn;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import java.util.*;

public class Bot implements Player {
    private String name = "Bot";
    private char color = 'r';
    private String token = "";
    private OpenAiService service;
    private List<ChatMessage> messages = new ArrayList<>();
    public Bot() {
        this.service = new OpenAiService(token);
        String systemStr = """
                You are about to play Connect Four on a grid represented by the given matrix.
                'u' tokens are empty slots.
                'r' tokens are yours.
                Any other tokens are opponent's token.
                Analise the board in order to win or block opponents and provide the best solution.
                """;
        ChatMessage systemMsg = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemStr);
        String constrainStr = """
                1) You can't place tokens that would overflow a column.
                2) Answer using the following format:"My answer is: <number>"
                3) IT'S VERY IMPORTANT TO FOLLOW THIS TWO RULES!
                """;
        ChatMessage constrainMsg = new ChatMessage(ChatMessageRole.SYSTEM.value(), constrainStr);
        String strategyStr = """
            The condition to win is to place four consecutive tokens in a row, column, or diagonal.
            You can try blocking your opponent's moves or try to win.
            """;
        ChatMessage strategyMsg = new ChatMessage(ChatMessageRole.SYSTEM.value(), strategyStr);
        messages.add(strategyMsg);
        messages.add(systemMsg);
        messages.add(constrainMsg);
    }

    public String getName() { return this.name; }

    public char getColor() { return this.color; }

    public int selectCol(Board board) {
        String messageString = "This is the actual board:\n" + board.toString()
                + "\nPick a number between 1 and " + board.getBoard().length + ": ";
        ChatMessage message = new ChatMessage(ChatMessageRole.USER.value(), messageString);
        messages.add(message);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .temperature(1.5)
                .n(1)
                .maxTokens(8)
                .build();
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        System.out.println(responseMessage);
        return filterColFromMessage(responseMessage);
    }

    private int filterColFromMessage(ChatMessage cm){
        return Integer.parseInt(cm.getContent().replaceAll("[^0-9]", ""))-1;
    }
}
