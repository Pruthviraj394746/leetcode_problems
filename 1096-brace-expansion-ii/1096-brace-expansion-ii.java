import java.util.*;

class Solution {

    private TreeSet<String> result = new TreeSet<>();

    public List<String> braceExpansionII(String expression) {
        dfs(expression);
        return new ArrayList<>(result);
    }

    private void dfs(String exp) {

        // No more braces → complete word
        int close = exp.indexOf('}');

        if (close == -1) {
            result.add(exp);
            return;
        }

        // Find the matching '{'
        int open = exp.lastIndexOf('{', close);

        String before = exp.substring(0, open);
        String inside = exp.substring(open + 1, close);
        String after = exp.substring(close + 1);

        // Split options by comma
        String[] choices = inside.split(",");

        for (String choice : choices) {
            dfs(before + choice + after);
        }
    }
}