package gorm

class UserInvitation {
    String code
    Boolean active

    static belongsTo = [user: User]
}
