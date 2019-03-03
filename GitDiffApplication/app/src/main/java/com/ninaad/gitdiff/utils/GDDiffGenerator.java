package com.ninaad.gitdiff.utils;

import android.content.Context;
import android.graphics.Color;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.models.GDGitDiffLine;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ninaad on 3/1/19.
 */
public class GDDiffGenerator {
    private static final String TAG = GDDiffGenerator.class.getName();
    private Context mContext;
    private static ArrayList<GDGitDiffLine> mPreviousDiff;
    private static ArrayList<GDGitDiffLine> mNextDiff;

    public GDDiffGenerator(Context mContext, String mDiff) {
        this.mContext = mContext;
        createPreviousDiff(mDiff);
    }
    /**
     * A public method to get the changes before the pull request
     * @return A list of lines specific to changes before the pull request
     */
    public ArrayList<GDGitDiffLine> getPreviousDiff() {
        return mPreviousDiff;
    }

    /**
     * A public method to get the changes after the pull request
     * @return A list of lines specific to changes after the pull request
     */
    public ArrayList<GDGitDiffLine> getNextDiff() {
        return mNextDiff;
    }

    /**
     * An internal method to compute the difference lists for each line,
     * with corresponding colors inspired from the Github split UI
     * @param mDiff The difference string from Git
     */
    private void createPreviousDiff(String mDiff){
        mPreviousDiff = new ArrayList<>();
        mNextDiff = new ArrayList<>();
        /*
          The difference string is split into lines
         */
        String[] mDiffArray = mDiff.split("\n");
        /*
          I found specific patterns to look out for calculating the differences
         */
        Pattern diffLinePattern = Pattern.compile("^(diff) --git .*?(/.*) .*?(/.*)");
        Pattern lineNumbersPattern = Pattern.compile("^(@@) .*?(\\d*),(\\d*) .*?(\\d*),(\\d*) " +
                "(@@)");
        Pattern removeLinePattern = Pattern.compile("^(-).*");
        Pattern addLinePattern = Pattern.compile("^(\\+).*");

        /*
          The line numbers to be prepended to each line specifying the difference
         */
        int currentPrevLineNumber = 0;
        int currentNextLineNumber = 0;
        /*
          I use the 'currentDiffCount' variable to keep track of the number of empty lines
          which are to be appended to either the lists, based on the differences
         */
        int currentDiffCount = 0;

        for (int i = 0; i < mDiffArray.length; i++) {
            StringBuilder builder = new StringBuilder();

            /*
              Matchers for every pattern, checking through every line
              to match the pattern
             */
            Matcher diffLineMatcher = diffLinePattern.matcher(mDiffArray[i]);
            Matcher lineNumbersMatcher = lineNumbersPattern.matcher(mDiffArray[i]);
            Matcher addLineMatcher = addLinePattern.matcher(mDiffArray[i]);
            Matcher removeLineMatcher = removeLinePattern.matcher(mDiffArray[i]);


            if (diffLineMatcher.find()){
                /*
                  If the line is a 'diff' line, then extract the file names and append them to
                  the corresponding lists
                 */
                mPreviousDiff.add(new GDGitDiffLine(String.format("%5s", "File") + "\t\t"
                        + diffLineMatcher.group(2), buildColor(R.color.diff_light_orange)));
                mNextDiff.add(new GDGitDiffLine(String.format("%5s", "File") + "\t\t"
                        + diffLineMatcher.group(3), buildColor(R.color.diff_light_orange)));
                /*
                  We don't consider the three lines after the 'diff' line as they have less
                  useful information about the differences that a user might not require
                 */
                i += 3;
                /*
                  These two 'while' loops are replicated in some cases to added empty
                  lines for proper alignment
                 */
                while (currentDiffCount < 0) {
                    mNextDiff.add(new GDGitDiffLine(String.format("%5s", "\t"),
                            buildColor(R.color.diff_gray)));
                    currentDiffCount++;
                }
                while (currentDiffCount > 0){
                    mPreviousDiff.add(new GDGitDiffLine(String.format("%5s", "\t"),
                            buildColor(R.color.diff_gray)));
                    currentDiffCount--;
                }
            } else if (lineNumbersMatcher.find()){
                /*
                  If the line contains the line numbers of the differences,
                  we initialize the line counts for the previous and next
                  differences
                 */
                while (currentDiffCount < 0) {
                    mNextDiff.add(new GDGitDiffLine(String.format("%5s", "\t"),
                            buildColor(R.color.diff_gray)));
                    currentDiffCount++;
                }
                while (currentDiffCount > 0){
                    mPreviousDiff.add(new GDGitDiffLine(String.format("%5s", "\t"),
                            buildColor(R.color.diff_gray)));
                    currentDiffCount--;
                }
                currentPrevLineNumber = Integer.parseInt(lineNumbersMatcher.group(2));
                currentNextLineNumber = Integer.parseInt(lineNumbersMatcher.group(4));

                builder.append(mDiffArray[i]);
                mPreviousDiff.add(new GDGitDiffLine(  String.format("%5s", "") + "\t\t" +
                        builder.toString(), buildColor(R.color.diff_light_blue)));
                mNextDiff.add(new GDGitDiffLine(  String.format("%5s", "") + "\t\t" +
                        builder.toString(), buildColor(R.color.diff_light_blue)));
            } else if (removeLineMatcher.find()){
                /*
                  If the line was present before the pull request,
                  we add the line to the previous pull list
                 */
                builder.append(mDiffArray[i]);
                mPreviousDiff.add(new GDGitDiffLine(  String.format("%5s", currentPrevLineNumber)
                        + "\t\t" + builder.toString(), buildColor(R.color.diff_light_red)));
                currentPrevLineNumber++;
                currentDiffCount--;
            } else if (addLineMatcher.find()){
                 /*
                  If the line was present after the pull request,
                  we add the line to the next pull list
                 */
                builder.append(mDiffArray[i]);
                mNextDiff.add(new GDGitDiffLine(  String.format("%5s", currentNextLineNumber)
                        + "\t\t" + builder.toString(), buildColor(R.color.diff_light_green)));
                currentNextLineNumber++;
                currentDiffCount++;
            } else {
                /*
                  If it is any other difference line, we append it to both the lists
                 */
                while (currentDiffCount < 0) {
                    mNextDiff.add(new GDGitDiffLine(String.format("%5s", "\t"),
                            buildColor(R.color.diff_gray)));
                    currentDiffCount++;
                }
                while (currentDiffCount > 0){
                    mPreviousDiff.add(new GDGitDiffLine(String.format("%5s", "\t"),
                            buildColor(R.color.diff_gray)));
                    currentDiffCount--;
                }
                builder.append(mDiffArray[i]);
                mPreviousDiff.add(new GDGitDiffLine(  String.format("%5s", currentPrevLineNumber)
                        + "\t\t" + builder.toString(), buildColor(R.color.diff_white)));
                mNextDiff.add(new GDGitDiffLine(  String.format("%5s", currentNextLineNumber)
                        + "\t\t" + builder.toString(), buildColor(R.color.diff_white)));
                currentPrevLineNumber++;
                currentNextLineNumber++;
            }
        }
    }

    /**
     * An internal method to get color from a resource
     * @param color The resource id of the required color
     * @return The integer value equivalent of the color
     */
    private int buildColor(int color){
        String colorBuilder = "#" +
                Integer.toHexString(mContext.getColor(color));
        return Color.parseColor(colorBuilder);
    }
}
