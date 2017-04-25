package gorm

class Book {

    String name

    static belongsTo = [user: User]
}
