package com.example.pracgrader.databases.schema;

public class DatabaseSchema {
    public static class AdminTable
    {
        public static final String NAME = "admins";
        public static class Cols
        {
            public static final String USERNAME = "username";
            public static final String PIN = "pin";
        }
    }

    public static class InstructorTable
    {
        public static final String NAME = "instructors";
        public static class Cols
        {
            public static final String USERNAME = "username";
            public static final String PIN = "pin";
            public static final String EMAIL = "email";
            public static final String COUNTRY = "country";
            public static final String NAME = "name";
        }
    }

    public static class StudentTable
    {
        public static final String NAME = "students";
        public static class Cols
        {
            public static final String USERNAME = "username";
            public static final String PIN = "pin";
            public static final String EMAIL = "email";
            public static final String COUNTRY = "country";
            public static final String NAME = "name";
            public static final String CREATOR = "creator";

        }
    }

    public static class PracTable
    {
        public static final String NAME = "pracs";
        public static class Cols
        {
            public static final String TITLE = "title";
            public static final String DIS = "description";
            public static final String MAXMARK = "maxmark";
        }
    }

    public static class MarksTable
    {
        public static final String NAME = "pracMarks";
        public static class Cols
        {
            public static final String TITLE = "title";
            public static final String USERNAME = "description";
            public static final String MARK = "mark";
        }
    }


}
