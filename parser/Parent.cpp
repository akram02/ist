#include<bits/stdc++.h>
#include <sqlite3.h>

#pragma once
using namespace std;

class Parent {
public:
    struct Course {
        int code, marks, hours;
        double credits;
        string title, major, year, semester, department;
        vector<string> content;
        vector<string> books;
    };
    vector<string> lines;
    vector<string> courseList;
    set<string> courseSet;
    vector<Course> courses;
    string s, major, year, semester;

    void debug() {
    }
    void inputLines() {
        while (getline(cin, s)) {
            if (s.starts_with(":")) {
                lines[lines.size()-1] += s;
            }
            else {
                lines.push_back(s);
            }
        }
    }

    virtual void addCode(Course &course, int &i) {
        s = lines[i];
        int code;
        stringstream ss(s);
        ss>>s>>s>>s>>code;
        if (lines[i].ends_with(':')) {
            i++;
            s = lines[i];
            stringstream st(s);
            st>>code;
        }
        course.code = code;
    }

    virtual void addMarks(Course &course, int i) {
        s = lines[i];
        int marks;
        stringstream ss(s);
        ss>>s>>marks;
        if (marks == 0) {
            stringstream st(lines[i]);
            st>>s>>s>>marks;
        }
        if (marks == 0) {
            ss>>marks;
        }
        course.marks = marks;
    }

    virtual void addCredits(Course &course, int i) {
        s = lines[i];
        double credits;
        stringstream ss(s);
        ss>>s>>s>>credits;
        if (credits == 0) {
            stringstream st(s);
            st>>s>>credits;
        }
        //credit :1.5
        if (s.starts_with(':') && s.length()>1 && isdigit(s[1])) {
            string t="";
            s = lines[i];
            for (auto c: s) {
                if (c != ':') t+=c;
            }
            stringstream st(t);
            st>>s>>credits;
        }
        course.credits = credits;
    }

    virtual void addHours(Course &course, int i) {
        s = lines[i];
        int hours;
        stringstream ss(s);
        ss>>s>>s>>hours;
        course.hours = hours;
    }

    void addTitle(Course &course, int i) {
        s = lines[i];
        course.title = s;
    }

    static string convertListToString(const vector<string>& v) {
        string result;
        for (const auto &item : v){
            result += item;
            result += "\n";
        }
        return result;
    }
    static string removeEndZero(string s) {
        while (s.size()>1 && s[s.size()-1]=='0') s.pop_back();
        return s;
    }
    static string removeEndDot(string s) {
        while (s.size()>1 && s[s.size()-1]=='.') s.pop_back();
        return s;
    }
    static string removeEndZeroAndDot(string s) {
        s = removeEndZero(s);
        s = removeEndDot(s);
        return s;
    }
    static string toLower(string s) {
        std::transform(s.begin(), s.end(),s.begin(), ::tolower);
        return s;
    }

    static string changeToNumericOrdinal(string s) {
        s = toLower(s);
        string arN[] = {"1","2","3","4","5","6","7","8"};
        string arCardinal[] = {"one","two","three","four","five","six","seven","eight"};
        string arOrdinal[] = {"first","second","third","fourth","fifth","sixth","seventh","eighth"};
        string arNumericOrdinal[] = {"1st","2nd","3rd","4th","5th","6th","7th","8th"};
        for (int i = 0; i < 8; ++i) {
            if (s == arN[i] || s == arCardinal[i] || s == arOrdinal[i] || s == arNumericOrdinal[i]) {
                s = arNumericOrdinal[i];
            }
        }
        return s;
    }

    void save(sqlite3 *db) {

        for (const auto &item : courses){

            string code = to_string(item.code);
            string marks = to_string(item.marks);
            string hour = to_string(item.hours);
            string credits = removeEndZeroAndDot(to_string(item.credits));
            string title = item.title;
            string major = item.major;
            string year = changeToNumericOrdinal(item.year);
            string semester = changeToNumericOrdinal(item.semester);
            string department = item.department;
            string content = convertListToString(item.content);
            string book = convertListToString(item.books);

            string sql = "INSERT INTO course (code, marks, hour, credit, title, major, year, semester, department, content, book)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            sqlite3_stmt *query = NULL;
            int ret;
            if (SQLITE_OK != (ret = sqlite3_prepare(db, sql.c_str(), -1, &query, NULL))) {
                printf("Failed to prepare insert: %d, %s\n", ret, sqlite3_errmsg(db));
            }
            else {
                sqlite3_bind_text(query, 1, code.c_str(), code.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 2, marks.c_str(), marks.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 3, hour.c_str(), hour.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 4, credits.c_str(), credits.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 5, title.c_str(), title.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 6, major.c_str(), major.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 7, year.c_str(), year.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 8, semester.c_str(), semester.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 9, department.c_str(), department.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 10, content.c_str(), content.length(), SQLITE_TRANSIENT);
                sqlite3_bind_text(query, 11, book.c_str(), book.length(), SQLITE_TRANSIENT);
            }
            if (sqlite3_step(query) != SQLITE_DONE) cout << "Didn't Insert Item!" << endl;
            sqlite3_finalize(query);
        }
    }

    void printResult() {
        for (auto c: courses) {
            cout<<"Course Code: "<<c.code<<endl;
            cout<<"Marks: "<<c.marks<<endl;
            cout<<"Credits: "<<c.credits<<endl;
            cout<<"Class Hours: "<<c.hours<<endl;
            cout<<"Major: "<<c.major<<endl;
            cout<<"Year: "<<c.year<<endl;
            cout<<"Semester: "<<c.semester<<endl;
            cout<<"Course Title: "<<c.title<<endl;
            cout<<"Content: "<<endl;
            for (const auto& content: c.content) {
                cout<<content<<endl;
            }
            cout<<endl;
            cout<<"Recommended Book:"<<endl;
            for (const auto& book: c.books) {
                cout<<book<<endl;
            }
            cout<<endl<<endl<<endl;
        }
    }
};