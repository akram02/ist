#include<bits/stdc++.h>
#include "Parent.cpp"
using namespace std;

class ECE : public Parent {
public:

    void extractBook(Course &course, int i) {
        string book;
        while (i<lines.size()) {
            s = lines[i];
            i++;
            if(s.starts_with("Page ")) continue;
            stringstream ss(s);
            string t;
            ss>>t;
            if (s.starts_with("Course Code :") || i==s.size() || (s.ends_with(" Semester") && s.length() < 30) || (s.ends_with(" Year") && s.length() < 30) || (s.starts_with("Year") && s.length() < 30)) {
                if(!book.empty())
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
            if (s.starts_with("Course Code :") || s.starts_with("Recommended Books:") || i==lines.size() || (s.ends_with(" Semester") && s.length() < 30)) {
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
//        addMarks(course, i+1);
        addCredits(course, i+1);
        addHours(course, i+2);
        addTitle(course, i+4);
        extractContent(course, i+5);
        course.major = major;
        course.year = year;
        course.semester = semester;
        course.department = "ECE";
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
    ECE() {
        ifstream stream("./../ece.txt");
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
            if (s.ends_with(" Semester") && s.length() < 30) {
                ss>>semester;
                stringstream st(lines[i+1]);
                st>>s;
                if(s.starts_with("Year"))
                    st>>year;
                else
                    year = s;
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
