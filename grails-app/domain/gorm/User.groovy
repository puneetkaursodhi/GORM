package gorm

class User {

    String name
    String email
    Date dob
    Boolean active = false
    Integer balance = 0

    static hasMany = [friends: User, books: Book]

    static constraints = {
        email(nullable: true)
        dob(nullable: true)
    }

    def afterInsert() {
        User.withNewSession {
            UserInvitation userInvitation = new UserInvitation(user: this)
            userInvitation.save()
        }
    }


}
