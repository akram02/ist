package bd.edu.ist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "course")
class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = 0

    @ColumnInfo(name = "uuid")
    var uuid: String? = UUID.randomUUID().toString()

    @ColumnInfo(name = "sync")
    var sync: Boolean? = false

    @ColumnInfo(name = "favorite")
    var favorite: Boolean? = false

    @ColumnInfo(name = "created_at")
    var createdAt: String? = ""

    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = ""

    @ColumnInfo(name = "code")
    var code : String? = ""

    @ColumnInfo(name = "marks")
    var marks : String? = ""

    @ColumnInfo(name = "hour")
    var hour : String? = ""

    @ColumnInfo(name = "credit")
    var credit : String? = ""

    @ColumnInfo(name = "title")
    var title : String? = ""

    @ColumnInfo(name = "major")
    var major : String? = ""

    @ColumnInfo(name = "year")
    var year : String? = ""

    @ColumnInfo(name = "semester")
    var semester : String? = ""

    @ColumnInfo(name = "department")
    var department : String? = ""

    @ColumnInfo(name = "content")
    var content : String? = ""

    @ColumnInfo(name = "book")
    var book : String? = ""
}
