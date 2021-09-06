#include<bits/stdc++.h>
#include "Parent.cpp"
using namespace std;

class CSE : public Parent {
public:

    void addHours(Course &course, int i) override {
        s = lines[i];
        int hours;
        stringstream ss(s);
        ss>>s>>s>>s>>hours;
        course.hours = hours;
    }

    void extractBook(Course &course, int i) {
        if (course.code == 510221) {
            cout<<course.content[0]<<endl;
            debug();
        }
        string book;
        while (i<lines.size()) {
            s = lines[i];
            i++;
            if(s.starts_with("Page ")) continue;
            stringstream ss(s);
            string t;
            ss>>t;
            if (s.starts_with("Course Code :") || i==s.size()) {
                i--;
                course.books.push_back(book);
                break;
            }
            if ((t.ends_with(".") || t.ends_with(")")) && !t.empty() && isdigit(t[t.size()-2])) {
                if(!book.empty()) {
                    course.books.push_back(book);
                }
                book = s;
            }
            else {
                book += " ";
                book += s;
            }
        }
    }

    void extractContent(Course &course, int i) {
        string content;
        if (course.code == 530207) {
            debug();
        }
        while (i<lines.size()) {
            s = lines[i];
            i++;
            if(s.starts_with("Page ")) continue;
            stringstream ss(s);
            string t;
            ss>>t;
            if (s.starts_with("Course Code :") || s.starts_with("Reference Books:") || i==lines.size()) {
                if(s.starts_with("Course Code :")) {
                    i--;
                }
                course.content.push_back(content);
                break;
            }
            if (((t.ends_with(".") || t.ends_with(")")) && !t.empty() && isdigit(t[t.size()-2])) || (s.find(':')!=variant_npos)) {
                if(!content.empty())
                    course.content.push_back(content);
                content = s;
            }
            else {
                content += " ";
                content += s;
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
        course.department = "CSE";
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
    CSE() {
        ifstream stream("./../cse.txt");
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
            if(s.starts_with("Course Code :")) {
                extractCourse(i, 6);
            }
            if(s.starts_with("BBA Program (Common Courses)")) {
                extractCourseString(i+1);
            }
        }
    }
};