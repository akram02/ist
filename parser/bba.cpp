#include<bits/stdc++.h>
#include "Parent.cpp"
using namespace std;

class BBA : public Parent {
public:
    void addCode(Course &course, int &i) override {
        s = lines[i];
        int code;
        stringstream ss(s);
        ss>>s>>s>>code;
        course.code = code;
    }

    void addCredits(Course &course, int i) override {
        s = lines[i];
        int credits;
        stringstream ss(s);
        ss>>credits;
        if (credits == 0) {
            stringstream st(s);
            st>>s>>credits;
        }
        course.credits = credits;
    }

    void extractBook(Course &course, int i) {
        string book;
        while (1) {
            s = lines[i];
            i++;
            if(s.ends_with(" | P a g e")) continue;
            stringstream ss(s);
            string t;
            ss>>t;
            if (t.ends_with(".") && !t.empty() && isdigit(t[t.size()-2])) {
                if(!book.empty()) {
                    course.books.push_back(book);
                }
                book = s;
            }
            else {
                book += " ";
                book += s;
            }
            if (s.empty() && lines[i].empty()) {
                course.books.push_back(book);
                break;
            }
        }
    }

    void extractContent(Course &course, int i) {
        string content;
        while (1) {
            s = lines[i];
            i++;
            if(s.ends_with(" | P a g e")) continue;
            stringstream ss(s);
            string t;
            ss>>t;
            if ((t.ends_with(".") && !t.empty() && isdigit(t[t.size()-2])) || t.starts_with("Part-")) {
                if(!content.empty())
                    course.content.push_back(content);
                content = s;
            }
            else {
                content += " ";
                content += s;
            }
            if (s.starts_with("Recommended Book")) {
                course.content.push_back(content);
                break;
            }
        }
        extractBook(course, i);
    }
    void extractCourse(int i, int n) {
        Course course;
        addCode(course, i);
        addMarks(course, i+1);
        addCredits(course, i+2);
        addHours(course, i+3);
        addTitle(course, i+5);
        extractContent(course, i+6);
        course.major = major;
        course.year = year;
        course.semester = semester;
        course.department = "BBA";
        courses.push_back(course);
    }

    void extractCourseString(int i) {
        while (i<lines.size()) {
            s = lines[i];
            if (s.starts_with("10 | P a g e")) {
                break;
            }
            i++;
            if(s.ends_with("Semester") || s.starts_with("BBA Major in ")) continue;
            if(s=="Organization") {
                courseList[courseList.size()-1] += " Organization";
                continue;
            }
            courseList.push_back(s);
        }
        for (auto & j : courseList) {
            if (courseSet.count(j)) {
                cout<<"HI"<<endl;
            }
            courseSet.insert(j);
        }
    }
    BBA() {
        ifstream stream("./../bba.txt");
        cin.rdbuf(stream.rdbuf());
        inputLines();

        for (int i = 0; i < lines.size(); ++i) {
            s = lines[i];
            if (s.starts_with("10 | P a g e")) {
                major = "";
            }
            stringstream ss(s);
            if (s.starts_with("BBA Major in ")) {
                ss>>s>>s>>s;
                ss>>major;
                while (ss >> s) {
                    major += " ";
                    major += s;
                }
            }
            if ((s.ends_with(" YEAR") || s.ends_with(" YEAR - MAJOR COURSES")) && s.length()<30) {
                ss>>year;
            }
            if (s.ends_with("Semester") && s.length() < 30) {
                ss>>semester;
            }
            if(s.starts_with("Course Code:") || s.starts_with("Course  Code:")) {
                extractCourse(i, 6);
            }
            if(s.starts_with("BBA Program (Common Courses)")) {
                extractCourseString(i+1);
            }
        }
    }
};