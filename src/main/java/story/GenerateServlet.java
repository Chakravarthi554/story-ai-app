package story;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/GenerateServlet")
public class GenerateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GenerateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String prompt = request.getParameter("prompt");
        String apiUrl = "https://chatgpt-ai-assistant.p.rapidapi.com/";
        String apiKey = "9a64173fd8msh71b3f483ce1edb8p11c901jsn6ae7842c55ac"; // ✅ Keep it secure in production

        String storyResult = "";
        String extraInfo = "";

        try {
            // --- ADD THIS LINE ---
            System.out.println("Prompt sent to API: " + prompt);
            // ---------------------

            // 1. Generate Main Story
            storyResult = getChatGPTResponse(prompt, apiUrl, apiKey);

            // 2. Generate Extra Info (assuming first call succeeded and you want to
            // continue)
            if (storyResult != null && !storyResult.isEmpty()) {
                String relatedPrompt = "Give additional or related information for the following story: " + storyResult;
                extraInfo = getChatGPTResponse(relatedPrompt, apiUrl, apiKey);
            } else {
                extraInfo = "Could not generate additional information due to main story generation failure.";
            }

            // Replace \n with <br> for HTML display
            storyResult = storyResult.replaceAll("\n", "<br>");
            extraInfo = extraInfo.replaceAll("\n", "<br>");

            request.setAttribute("story", storyResult);
            request.setAttribute("extraInfo", extraInfo);
            request.setAttribute("prompt", prompt);

        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace to console for detailed error
            request.setAttribute("error", "Error generating content: " + e.getMessage());
        }

        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

    private String getChatGPTResponse(String prompt, String apiUrl, String apiKey) throws Exception {
        String jsonInputString = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"" + prompt + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("x-rapidapi-key", apiKey);
        con.setRequestProperty("x-rapidapi-host", "chatgpt-ai-assistant.p.rapidapi.com");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        StringBuilder responseText = new StringBuilder();
        int status = con.getResponseCode();

        if (status > 299) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getErrorStream() != null ? con.getErrorStream() : con.getInputStream(),
                            "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    responseText.append(line.trim());
                }
            }
            System.err.println("API Error Response (Status " + status + "): " + responseText.toString());
            throw new IOException("API request failed with status " + status + ": " + responseText.toString());
        } else {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    responseText.append(line.trim());
                }
            }
        }

        System.out.println("Raw API Response: " + responseText.toString());

        JSONObject jsonResponse = new JSONObject(responseText.toString());

        if (!jsonResponse.has("choices") || jsonResponse.getJSONArray("choices").isEmpty()) {
            throw new IOException("API response does not contain 'choices' array or it is empty. Full response: "
                    + responseText.toString());
        }

        JSONArray choices = jsonResponse.getJSONArray("choices");
        JSONObject message = choices.getJSONObject(0).getJSONObject("message");

        if (!message.has("content") || message.isNull("content")) {
            throw new IOException("API response message does not contain 'content' field or it is null. Full response: "
                    + responseText.toString());
        }

        return message.getString("content");
    }
}