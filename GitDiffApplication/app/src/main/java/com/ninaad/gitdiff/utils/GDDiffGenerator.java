package com.ninaad.gitdiff.utils;

import android.content.Context;
import android.graphics.Color;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.models.GDGitDiffLine;

import java.util.ArrayList;

/**
 * Created by ninaad on 3/1/19.
 */
public class GDDiffGenerator {
    private static final String TAG = GDDiffGenerator.class.getName();
    private Context mContext;
    private ArrayList<GDGitDiffLine> mPreviousDiff;
    private ArrayList<GDGitDiffLine> mNextDiff;
    private static final int[] GIT_COLORS = {
            R.color.diff_light_blue,
            R.color.diff_light_green,
            R.color.diff_light_red,
            R.color.diff_gray,
            R.color.diff_white,
            R.color.diff_light_orange};

    public GDDiffGenerator(Context mContext, String mDiff) {
        this.mContext = mContext;
        this.mPreviousDiff = createPreviousDiff(mDiff);
        this.mNextDiff = createNextDiff(mDiff);
    }

    public ArrayList<GDGitDiffLine> getPreviousDiff() {
        return mPreviousDiff;
    }

    public ArrayList<GDGitDiffLine> getNextDiff() {
        return mNextDiff;
    }

    private ArrayList<GDGitDiffLine> createPreviousDiff(String mDiff){
        ArrayList<GDGitDiffLine> mOutput = new ArrayList<>();
        String [] mDiffArray = mDiff.split("\n");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < mDiffArray.length; i++) {
//            Pattern pattern = Pattern.compile("'(.*?)'");
//            Matcher matcher = pattern.matcher(mDiffArray[i]);
//            if (matcher.find())
//            {
//                System.out.println(matcher.group(1));
//            }
//            String [] mLineArray = mDiffArray[i].;
//            if (i == 0) {
////                Log.i(TAG, " color = " + Integer.toHexString(mContext.getColor(R.color
////                        .diff_light_blue) & 0x00FFFFFF).toUpperCase());
//                mOutput.append(String.format("<div style='background-color:#%s;",
//                        Integer.toHexString(mContext.getColor(R.color
//                                .diff_light_blue) & 0x00FFFFFF).toUpperCase()));
//                mOutput.append("width:100%;'>");
//                mOutput.append("&emsp;");
//                mOutput.append(String.format("%1$4s", i));
//                mOutput.append("&emsp;");
//                mOutput.append(mDiffArray[i]);
////                mOutput.append("&emsp;");
//                mOutput.append("</div>");
////                mOutput.append("<br>");
//                Log.d(TAG, "prev output = " + mOutput.toString());
//            } else {
//                mOutput.append(String.format("%1$4s", i));
//                mOutput.append("&emsp;");
//                mOutput.append(mDiffArray[i]);
//                mOutput.append("&emsp;");
//                mOutput.append("<br>");
//            }
            GDGitDiffLine gitDiffLine = new GDGitDiffLine(i + "\t\t" + mDiffArray[i],
                    buildColor(GIT_COLORS[i % 6]));
            mOutput.add(gitDiffLine);
        }
        return mOutput;
    }

    private ArrayList<GDGitDiffLine> createNextDiff(String mDiff){
        ArrayList<GDGitDiffLine> mOutput = new ArrayList<>();
        String [] mDiffArray = mDiff.split("\n");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mDiffArray.length; i++) {
//            if (i == 0) {
//                mOutput.append(String.format("<div style='background-color:#%s;",
//                        Integer.toHexString(mContext.getColor(R.color
//                                .diff_light_blue) & 0x00FFFFFF).toUpperCase()));
//                mOutput.append("width:100%;'>");
//                mOutput.append("&emsp;");
//                mOutput.append(String.format("%1$4s", i));
//                mOutput.append("&emsp;");
//                mOutput.append(mDiffArray[i]);
//                mOutput.append("</div>");
////                mOutput.append("<br>");
//            } else {
//                mOutput.append(String.format("%1$4s", i));
//                mOutput.append("&emsp;");
//                mOutput.append(mDiffArray[i]);
//                mOutput.append("&emsp;");
//                mOutput.append("<br>");
//            }
            GDGitDiffLine gitDiffLine = new GDGitDiffLine(i + "\t\t" + mDiffArray[i],
                    buildColor(GIT_COLORS[i % 6]));
            mOutput.add(gitDiffLine);
        }
        return mOutput;
    }

    private int buildColor(int color){
        String colorBuilder = "#" +
                Integer.toHexString(mContext.getColor(color));
        return Color.parseColor(colorBuilder);
    }
}
