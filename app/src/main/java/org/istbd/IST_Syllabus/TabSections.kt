package org.istbd.IST_Syllabus

enum class TabSections(
    val title: String,
    val icon: Int,
    val route: String
) {
    CSE("CSE", R.drawable.cse_button, "cse"),
    BBA("BBA", R.drawable.bba_button, "bba"),
    ECE("ECE", R.drawable.ece_button, "ece"),
    CREDIT("Credit", R.drawable.credit_button, "credit")
}

