package com.mobitech.maingoquizagain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobitech.maingoquizagain.QuizContract.*;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

   private static final String DATABASE_NAME = "GoQuiz.db";
   private static final int DATBASE_VERSION = 1;

    private SQLiteDatabase db;


    QuizDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATBASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        //fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }


    private void fillQuestionsTable()
    {

        Questions q1 = new Questions("Android is what ?","OS","Drivers","Software","Hardware",1);
        addQuestions(q1);


        Questions q2 = new Questions("Full form of PC is ?","OS","Personal Computer","Pocket Computer","Hardware",2);
        addQuestions(q2);


        Questions q3 = new Questions("Windows is what ?","Easy Software","Hardware Device","Operating System","Hardware",3);
        addQuestions(q3);


        Questions q4 = new Questions("Unity is used for what ?","Game Development","Movie Making","Firmware","Hardware",1);
        addQuestions(q4);


        Questions q5 = new Questions("RAM stands for ","Windows","Drivers","GUI","Random Access Memory",4);
        addQuestions(q5);


        Questions q6 = new Questions("Chrome is what ?","OS","Browser","Tool","New Browser",2);
        addQuestions(q6);

        Questions q7 = new Questions("C invented by ?","Dannies","J.k Rowling","Tool","New Browser",1);
        addQuestions(q7);

        Questions q8 = new Questions("Java developed by ?","Dannies","Sun","Oracle","Microsoft",2);
        addQuestions(q8);
    }
    //Adding data.........
    public Boolean insertQuestion(String ques,String op1,String op2,String op3,String op4,int answer){
        db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,ques);
        cv.put(QuestionTable.COLUMN_OPTION1,op1);
        cv.put(QuestionTable.COLUMN_OPTION2,op2);
        cv.put(QuestionTable.COLUMN_OPTION3,op3);
        cv.put(QuestionTable.COLUMN_OPTION4,op4);
        cv.put(QuestionTable.COLUMN_ANSWER_NR,answer);

        long result = -1;

        try {
            result = db.insert(QuestionTable.TABLE_NAME,null,cv);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(result > -1)
            return true;
        else
            return false;
    }

    //Getting Task................

    private void addQuestions(Questions question){

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4,question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        db.insert(QuestionTable.TABLE_NAME,null,cv);

    }

    public ArrayList<Questions> getAllQuestions() {

        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();



        String Projection[] = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR
        };



            Cursor c = db.query(QuestionTable.TABLE_NAME,
                    Projection,
                    null,
                    null,
                    null,
                    null,
                    null);


            if (c.moveToFirst()) {
                do {

                    Questions question = new Questions();
                    question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                    question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                    question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                    question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                    question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                    question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));

                    questionList.add(question);

                } while (c.moveToNext());

            }
            c.close();
            return questionList;

        }

    public ArrayList<Questions> getAllData(){

        ArrayList<Questions>alist=new ArrayList<Questions>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("Select * from " + QuestionTable.TABLE_NAME,null);

        if(cr!=null && cr.getCount() > 0){
            try {
                while (cr.moveToNext()){
                    Questions sg = new Questions();
                    sg.setQuestion(cr.getString(cr.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                    sg.setOption1(cr.getString(cr.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                    sg.setOption2(cr.getString(cr.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                    sg.setOption3(cr.getString(cr.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                    sg.setOption4(cr.getString(cr.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                    sg.setAnswerNr(cr.getInt(cr.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                    alist.add(sg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(!cr.isClosed())
                    cr.close();
            }
        }

        return alist;
    }

    }


