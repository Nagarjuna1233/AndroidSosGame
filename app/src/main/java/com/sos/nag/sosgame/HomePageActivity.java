package com.sos.nag.sosgame;


import android.app.AlertDialog;
import android.graphics.Color;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import com.sos.nag.service.WordTesting;

import java.io.IOException;


public class HomePageActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final int FIELD_WIDTH = 100;
    private static final int FIELD_HEIGH = 100;
    private int rowCount = 5;
    private int columnCount = 5;
    static int points1 = 0, points2 = 0, pointsetter = 0;

    private WordTesting w;
    private TextView meaningTF;
    private Button lockAnws;
    private TextView pintsTF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            w = new WordTesting("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_home_page);
        Log.d("--", "R-Lenght--"+rowCount+"   "+"C-Lenght--"+columnCount);
       // ScrollView sv = new ScrollView(this);
        TableLayout tableLayout = createTableLayout(rowCount,columnCount);
//        HorizontalScrollView hsv = new HorizontalScrollView(this);
//        hsv.addView(tableLayout);
//        sv.addView(hsv);
//        setContentView(sv);
}

    public void makeCellEmpty(TableLayout tableLayout, int rowIndex, int columnIndex) {
        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView) tableRow.getChildAt(columnIndex);

        // make it black
        textView.setBackgroundColor(Color.BLACK);
    }

    public void setHeaderTitle(TableLayout tableLayout, int rowIndex, int columnIndex) {

        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView) tableRow.getChildAt(columnIndex);

        textView.setText("Hello");
    }


    private TableLayout createTableLayout(int rc, int cc) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.homepage);
        tableLayout.setBackgroundColor(Color.BLACK);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int i = 0; i < rc; i++) {
            // 3) create tableRow
            TableRow tableRow = new TableRow(HomePageActivity.this);
            tableRow.setBackgroundColor(Color.GRAY);
            int etIdsCount = 0;
            for (int j = 0; j < cc; j++) {
                // 4) create textView
                EditText et = new EditText(HomePageActivity.this);
                //  textView.setText(String.valueOf(j));
                et.setBackgroundColor(Color.YELLOW);
                et.setGravity(Gravity.CENTER);
                et.setWidth(FIELD_WIDTH);
                et.setHeight(FIELD_HEIGH);
                et.setOnTouchListener(HomePageActivity.this);
                Log.d("TAG", "-___>" + et.getId());
                String compoundid = String.valueOf(i).concat(String.valueOf(j));
                et.setId(Integer.valueOf(compoundid));
                // et.setText(compoundid);
                // 5) add textView to tableRow
                tableRow.addView(et, tableRowParams);
                etIdsCount++;
            }
            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }

        TableRow tableRow = new TableRow(HomePageActivity.this);
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;
        tableRow.setBackgroundColor(Color.GRAY);


        lockAnws=new Button(HomePageActivity.this);
        lockAnws.setText("Lock Ans");
        tableRow.addView(lockAnws, tableRowParams);

//        ponts1TF=new TextView(HomePageActivity.this);
//        ponts1TF.setText("P1:0");
//        tableRow.addView(ponts1TF, tableRowParams);
//
//        ponts2TF=new TextView(HomePageActivity.this);
//        ponts2TF.setText("P2:0");
//        tableRow.addView(ponts2TF, tableRowParams);

        meaningTF=new TextView(HomePageActivity.this);
        meaningTF.setText("meaning");
        tableRow.addView(meaningTF, tableRowParams);
        tableLayout.addView(tableRow, tableLayoutParams);
        return tableLayout;
    }

    @Override
    public boolean onTouch(View sourceView, MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()) {
            if (sourceView instanceof TextView) {
                EditText sourceEV = (EditText) findViewById(sourceView.getId());
                String sourceText = sourceEV.getText().toString();
                Toast.makeText(getApplicationContext(), "selected TV value :" + sourceText, Toast.LENGTH_SHORT).show();
                if ("".equals(sourceText)) {
                    Toast.makeText(getApplicationContext(), "onTouch: Up", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (sourceText.length() != 1) {
                    Toast.makeText(getApplicationContext(), "PLZ ENTER ONLY ONE CHRACTER", Toast.LENGTH_SHORT).show();
                    return false;
                }
                int sourceEVID = sourceEV.getId();
                int rowNum;
                int columnNum;
                if (sourceEVID / 10 == 0) {
                    rowNum = 0;
                    columnNum = sourceEVID;
                } else {
                    rowNum = sourceEVID / 10;
                    columnNum = sourceEVID % 10;
                }
                Log.d("Tag", "" + rowNum + " " + columnNum);

                if (rowNum == rowCount - 1 && columnNum == columnCount - 1) {

                    sourceEV.setActivated(false);
                    int flag = 0;

                    String xupper1id = String.valueOf(rowNum - 1).concat(String.valueOf(columnNum));
                    EditText xupper1ET = (EditText) findViewById(Integer.valueOf(xupper1id));
                    String xupper1Text = xupper1ET.getText().toString();

                    String yupper1id = String.valueOf(rowNum).concat(String.valueOf(columnNum - 1));
                    EditText yupper1ET = (EditText) findViewById(Integer.valueOf(yupper1id));
                    String yupper1Text = yupper1ET.getText().toString();

                    Toast.makeText(getApplicationContext(), yupper1Text + ":" + xupper1Text, Toast.LENGTH_SHORT).show();

                    if (xupper1Text.equals("") && yupper1Text.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            // meaning.setText(sourceText);

                        } else {
                            meaningTF.setText("No meaning found");
                        }

                    } else {
                        String col_word = sourceText, row_word = sourceText, finalRowWord = null, finalColumnWord = null;
                        //coldata

                        for (int k = rowNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                col_word += tempid;

                            } else {
                                break;
                            }

                        }
                        StringBuffer sb = new StringBuffer(col_word);
                        finalColumnWord = sb.reverse().toString();


                        //rowdata
                        for (int k = columnNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                row_word += tempText;
                            } else
                                break;
                        }
                        sb = new StringBuffer(row_word);
                        finalRowWord = sb.reverse().toString();
                        if (w.forwardTesting(finalColumnWord)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(finalRowWord)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        //JOptionPane.showMessageDialog(this,"noming found");

                    }//inner else closing

                } else if (rowNum == 0 && columnNum == columnCount - 1) {
                    sourceEV.setActivated(false);
                    String xupper1id = String.valueOf(rowNum + 1).concat(String.valueOf(columnNum));
                    EditText xupper1ET = (EditText) findViewById(Integer.valueOf(xupper1id));
                    String xupper1Text = xupper1ET.getText().toString();

                    String yupper1id = String.valueOf(rowNum).concat(String.valueOf(columnNum - 1));
                    EditText yupper1ET = (EditText) findViewById(Integer.valueOf(yupper1id));
                    String yupper1Text = yupper1ET.getText().toString();

                    if (xupper1Text.equals("") && xupper1Text.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {

                        String col_word = sourceText, row_word = sourceText,
                                finalRowWord = null;
                        // coldata

                        for (int k = rowNum + 1; k < rowCount; k++) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                col_word += tempText;

                            } else
                                break;

                        }

                        // rowdata
                        for (int k = columnNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(columnNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                row_word += tempText;
                            } else
                                break;
                        }
                        StringBuffer sb = new StringBuffer(row_word);
                        finalRowWord = sb.reverse().toString();
                        if (w.forwardTesting(col_word)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(finalRowWord)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }
                } else if (rowNum == 0 && columnNum == 0) {
                    sourceEV.setActivated(false);
                    String xupper1id = String.valueOf(rowNum + 1).concat(String.valueOf(columnNum));
                    EditText xupper1ET = (EditText) findViewById(Integer.valueOf(xupper1id));
                    String xupper1Text = xupper1ET.getText().toString();

                    String yupper1id = String.valueOf(rowNum).concat(String.valueOf(columnNum + 1));
                    EditText yupper1ET = (EditText) findViewById(Integer.valueOf(yupper1id));
                    String yupper1Text = yupper1ET.getText().toString();

                    if (xupper1Text.equals("") && yupper1Text.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {
                        String col_word = sourceText, row_word = sourceText;
                        // coldata

                        for (int k = rowNum + 1; k < rowCount; k++) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                col_word += sourceText;

                            } else
                                break;

                        }

                        // rowdata
                        for (int k = columnNum + 1; k < columnCount; k++) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                row_word += tempText;
                            } else
                                break;
                        }

                        if (w.forwardTesting(col_word)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(row_word)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }


                } else if (rowNum == (rowCount - 1) && columnNum == 0) {
                    sourceEV.setActivated(false);
                    String xupper1id = String.valueOf(rowNum - 1).concat(String.valueOf(columnNum));
                    EditText xupper1ET = (EditText) findViewById(Integer.valueOf(xupper1id));
                    String xupper1Text = xupper1ET.getText().toString();

                    String yupper1id = String.valueOf(rowNum).concat(String.valueOf(columnNum + 1));
                    EditText yupper1ET = (EditText) findViewById(Integer.valueOf(yupper1id));
                    String yupper1Text = yupper1ET.getText().toString();

                    if (xupper1Text.equals("") && yupper1Text.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {
                        String col_word = sourceText, row_word = sourceText,
                                finalColumnWord = null;
                        // coldata

                        for (int k = rowNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                col_word += tempText;

                            } else
                                break;

                        }

                        // rowdata
                        for (int k = columnNum + 1; k < columnCount; k++) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                row_word += tempText;
                            } else
                                break;
                        }
                        StringBuffer sb = new StringBuffer(col_word);
                        finalColumnWord = sb.reverse().toString();
                        if (w.forwardTesting(finalColumnWord)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(row_word)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }

                } else if ((rowNum < rowCount - 1 && columnNum < columnCount - 1) && (rowNum > 0 && columnNum > 0)) {

                    sourceEV.setActivated(false);
                    String upid = String.valueOf(rowNum - 1).concat(String.valueOf(columnNum));//up
                    EditText upET = (EditText) findViewById(Integer.valueOf(upid));
                    String upText = upET.getText().toString();

                    String rightid = String.valueOf(rowNum).concat(String.valueOf(columnNum + 1));//right
                    EditText rightET = (EditText) findViewById(Integer.valueOf(rightid));
                    String rightText = rightET.getText().toString();


                    String downid = String.valueOf(rowNum + 1).concat(String.valueOf(columnNum));//down
                    EditText downET = (EditText) findViewById(Integer.valueOf(downid));
                    String downText = downET.getText().toString();

                    String leftid = String.valueOf(rowNum).concat(String.valueOf(columnNum - 1));//left
                    EditText leftET = (EditText) findViewById(Integer.valueOf(leftid));
                    String leftText = leftET.getText().toString();

                    if (upText.equals("") && rightText.equals("") && downText.equals("")
                            && leftText.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {
                        String colup_word = sourceText, coldown_word = sourceText,
                                col_word = null;
                        /****** coldata *****/
                        // up
                        for (int k = rowNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                colup_word += tempText;

                            } else
                                break;

                        }

                        // down
                        for (int k = rowNum + 1; k < rowCount; k++) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                // System.out.println(coldown_word);
                                coldown_word += tempText;
                            } else
                                break;
                        }
                        StringBuffer sb = new StringBuffer(colup_word);
                        colup_word = sb.reverse().toString();
                        char[] colup_char = colup_word.toCharArray();
                        colup_word = String.copyValueOf(colup_char, 0, colup_char.length - 1);
                        col_word = colup_word.concat(coldown_word);
                        // System.out.println(col_word);
                        /***** rowdata *****/

                        String rowleft_word = sourceText, rowright_word = sourceText,
                                row_word = null;
                        /****** coldata *****/
                        // left
                        for (int k = columnNum - 1; k >= 0; k--) {

                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                rowleft_word += tempText;

                            } else
                                break;

                        }

                        // right
                        for (int k = columnNum + 1; k < columnCount; k++) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                // System.out.println(coldown_word);
                                rowright_word += tempText;
                            } else
                                break;
                        }
                        sb = new StringBuffer(rowleft_word);
                        rowleft_word = sb.reverse().toString();
                        char[] rowleft_char = rowleft_word.toCharArray();
                        rowleft_word = String.copyValueOf(rowleft_char, 0, rowleft_char.length - 1);
                        row_word = rowleft_word.concat(rowright_word);
                        // System.out.println(row_word);
                        if (w.forwardTesting(col_word)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(row_word)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }
                } // else if closing
                else if (columnNum == columnCount - 1) {
                    sourceEV.setActivated(false);
                    String upid = String.valueOf(rowNum - 1).concat(String.valueOf(columnNum));//up
                    EditText upET = (EditText) findViewById(Integer.valueOf(upid));
                    String upText = upET.getText().toString();

                    String downid = String.valueOf(rowNum + 1).concat(String.valueOf(columnNum));//down
                    EditText downET = (EditText) findViewById(Integer.valueOf(downid));
                    String downText = downET.getText().toString();

                    String leftid = String.valueOf(rowNum).concat(String.valueOf(columnNum - 1));//left
                    EditText leftET = (EditText) findViewById(Integer.valueOf(leftid));
                    String leftText = leftET.getText().toString();

                    if (upText.equals("") && downText.equals("") && leftText.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {
                        String colup_word = sourceText, coldown_word = sourceText,
                                col_word = null;
                        /****** coldata *****/
                        // up
                        for (int k = rowNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                colup_word += tempText;

                            } else
                                break;

                        }

                        // down
                        for (int k = rowNum + 1; k < rowCount; k++) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                // System.out.println(coldown_word);
                                coldown_word += tempText;
                            } else
                                break;
                        }
                        StringBuffer sb = new StringBuffer(colup_word);
                        colup_word = sb.reverse().toString();
                        char[] colup_char = colup_word.toCharArray();
                        colup_word = String.copyValueOf(colup_char, 0, colup_char.length - 1);
                        col_word = colup_word.concat(coldown_word);
                        // System.out.println(col_word);
                        /***** rowdata *****/

                        String row_word = sourceText;
                        for (int k = columnNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                row_word += tempText;
                            } else
                                break;
                        }
                        sb = new StringBuffer(row_word);
                        String r_word = sb.reverse().toString();
                        // System.out.println(r_word);
                        if (w.forwardTesting(col_word)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(r_word)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else {
                            meaningTF.setText("No meaning found");
                        }
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }
                } else if (rowNum == 0) {
                    sourceEV.setActivated(false);
                    String rightid = String.valueOf(rowNum).concat(String.valueOf(columnNum + 1));//right
                    EditText rightET = (EditText) findViewById(Integer.valueOf(rightid));
                    String rightText = rightET.getText().toString();

                    String downid = String.valueOf(rowNum + 1).concat(String.valueOf(columnNum));//down
                    EditText downET = (EditText) findViewById(Integer.valueOf(downid));
                    String downText = downET.getText().toString();

                    String leftid = String.valueOf(rowNum).concat(String.valueOf(columnNum - 1));//left
                    EditText leftET = (EditText) findViewById(Integer.valueOf(leftid));
                    String leftText = leftET.getText().toString();

                    if (rightText.equals("") && downText.equals("") && leftText.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {

                        /****** coldata *****/
                        String col_word = sourceText;
                        // coldata

                        for (int k = rowNum + 1; k < rowCount; k++) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                col_word += tempText;

                            } else
                                break;

                        }
                        /***** rowdata *****/

                        String rowleft_word = sourceText, rowright_word = sourceText,
                                row_word = null;

                        // left
                        for (int k = columnNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                rowleft_word += tempText;

                            } else
                                break;

                        }

                        // right
                        for (int k = columnNum + 1; k < columnCount; k++) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                // System.out.println(coldown_word);
                                rowright_word += tempText;
                            } else
                                break;
                        }
                        StringBuffer sb = new StringBuffer(rowleft_word);
                        rowleft_word = sb.reverse().toString();
                        char[] rowleft_char = rowleft_word.toCharArray();
                        rowleft_word = String.copyValueOf(rowleft_char, 0, rowleft_char.length - 1);
                        row_word = rowleft_word.concat(rowright_word);
                        // System.out.println(row_word);

                        if (w.forwardTesting(col_word)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(row_word)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }
                } // else if closing
                else if (columnNum == 0) {
                    sourceEV.setActivated(false);


                    String rightid = String.valueOf(rowNum).concat(String.valueOf(columnNum + 1));//right
                    EditText rightET = (EditText) findViewById(Integer.valueOf(rightid));
                    String rightText = rightET.getText().toString();


                    String downid = String.valueOf(rowNum + 1).concat(String.valueOf(columnNum));//down
                    EditText downET = (EditText) findViewById(Integer.valueOf(downid));
                    String downText = downET.getText().toString();

                    String upid = String.valueOf(rowNum - 1).concat(String.valueOf(columnNum));//up
                    EditText upET = (EditText) findViewById(Integer.valueOf(upid));
                    String upText = upET.getText().toString();


                    if (rightText.equals("") && downText.equals("") && upText.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {

                        /****** coldata *****/
                        String colup_word = sourceText, row_word = sourceText,
                                coldown_word = sourceText, col_word = null;
                        /****** coldata *****/
                        // up
                        for (int k = rowNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                colup_word += tempText;

                            } else
                                break;

                        }

                        // down
                        for (int k = rowNum + 1; k < rowCount; k++) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                // System.out.println(coldown_word);
                                coldown_word += tempText;
                            } else
                                break;
                        }
                        StringBuffer sb = new StringBuffer(colup_word);
                        colup_word = sb.reverse().toString();
                        char[] colup_char = colup_word.toCharArray();
                        colup_word = String.copyValueOf(colup_char, 0, colup_char.length - 1);
                        col_word = colup_word.concat(coldown_word);
                        // System.out.println(col_word);
                        /***** rowdata *****/
                        for (int k = columnNum + 1; k < columnCount; k++) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                row_word += tempText;
                            } else
                                break;
                        }

                        if (w.forwardTesting(col_word)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(row_word)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }
                } // else if closing
                else {
                    sourceEV.setActivated(false);
                    String upid = String.valueOf(rowNum - 1).concat(String.valueOf(columnNum));//up
                    EditText upET = (EditText) findViewById(Integer.valueOf(upid));
                    String upText = upET.getText().toString();

                    String rightid = String.valueOf(rowNum).concat(String.valueOf(columnNum + 1));//right
                    EditText rightET = (EditText) findViewById(Integer.valueOf(rightid));
                    String rightText = rightET.getText().toString();


                    String leftid = String.valueOf(rowNum).concat(String.valueOf(columnNum - 1));//left
                    EditText leftET = (EditText) findViewById(Integer.valueOf(leftid));
                    String leftText = leftET.getText().toString();


                    if (upText.equals("") && rightText.equals("") && leftText.equals("")) {
                        if (!(w.points_words.contains(sourceText)) && w.dic.contains(sourceText)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            // meaning.setText("");
                            w.points_words.add(sourceText);
                            meaningTF.setText(sourceText);

                        } else
                            meaningTF.setText("No meaning found");
                    } else {
                        String col_word = sourceText, row_word = sourceText;
                        // coldata

                        for (int k = rowNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(k).concat(String.valueOf(columnNum));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                col_word += tempText;

                            } else
                                break;

                        }

                        // rowdata
                        String rowleft_word = sourceText, rowright_word = sourceText;

                        // left
                        for (int k = columnNum - 1; k >= 0; k--) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {

                                rowleft_word += tempText;

                            } else
                                break;

                        }

                        // right
                        for (int k = columnNum + 1; k < columnCount; k++) {
                            String tempid = String.valueOf(rowNum).concat(String.valueOf(k));
                            EditText tempidET = (EditText) findViewById(Integer.valueOf(tempid));
                            String tempText = tempidET.getText().toString();
                            if (!(tempText.equals(""))) {
                                // System.out.println(coldown_word);
                                rowright_word += tempText;
                            } else
                                break;
                        }
                        StringBuffer sb = new StringBuffer(rowleft_word);
                        rowleft_word = sb.reverse().toString();
                        char[] rowleft_char = rowleft_word.toCharArray();
                        rowleft_word = String.copyValueOf(rowleft_char, 0, rowleft_char.length - 1);
                        // row_word=rowleft_word.concat(rowright_word);
                        System.out.println(row_word);
                        if (w.forwardTesting(col_word)) {

                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else if (w.forwardTesting(row_word)) {
                            if (pointsetter % 2 == 0)
                                points1++;
                            else
                                points2++;
                            meaningTF.setText(w.meaning_word);
                        } else
                            meaningTF.setText("No meaning found");
                        // JOptionPane.showMessageDialog(this,"noming
                        // found");

                    }
                } // else if closing
                pointsetter++;
            }
        }
        //ponts1TF.setText("P1:" + points1);
       // ponts2TF.setText("P2:" + points2);
        return false;
    }

    public void showDilogBox(String meg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);

        alertDialogBuilder.setTitle(HomePageActivity.this.getTitle() + " decision");

        alertDialogBuilder.setMessage(meg);

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

    }
}
