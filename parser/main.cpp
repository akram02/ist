#include<bits/stdc++.h>
#include <sqlite3.h>
#include "cse.cpp"
#include "ece.cpp"
#include "bba.cpp"
using namespace std;

string courseTableCreateQuery();

int main() {

    sqlite3 *db;

    sqlite3_open_v2("./../ist.db", &db, SQLITE_OPEN_READWRITE | SQLITE_OPEN_CREATE, NULL);

    string createQuery = courseTableCreateQuery();
    sqlite3_stmt *createStmt;
    sqlite3_prepare(db, createQuery.c_str(), createQuery.size(), &createStmt, NULL);
    if (sqlite3_step(createStmt) != SQLITE_DONE) cout << "Didn't Create Table!" << endl;

    CSE cse = CSE();
    cse.printResult();

    ECE ece = ECE();
    ece.printResult();

    BBA bba = BBA();
    bba.printResult();

    cse.save(db);
    ece.save(db);
    bba.save(db);
}


string courseTableCreateQuery() {
    string createQuery = "CREATE TABLE IF NOT EXISTS course"
                         "(id INTEGER PRIMARY KEY, uuid TEXT, sync INTEGER, favorite INTEGER, created_at TEXT, updated_at TEXT,"
                         "code TEXT, marks TEXT, hour TEXT, credit TEXT, title TEXT, major TEXT, year TEXT, semester TEXT, department TEXT, content TEXT, book TEXT"
                         ")";
    return createQuery;
}

