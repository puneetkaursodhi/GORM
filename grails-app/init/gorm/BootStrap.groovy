package gorm

class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        countRecords()
    }

    List<User> createUsers() {
        List<User> users = []
        (1..10).each {
            User user = new User(name: "user ${it}", email: "user+${it}@gmail.com", dob: new Date() - it, balance: 1000 * it)
            if (user.save()) {
                users.add(user)
                log.info "User ${user} saved successfully"
            } else {
                log.error "Error saving user : ${user.errors.allErrors}"
            }
        }
        users
    }

    void countRecords() {
        Integer count = User.count()
        log.info "Count : ${count}"
        Integer count1 = User.countByName("uday")
        log.info "countByName : ${count1}"
        Integer count2 = User.countByNameIlikeAndBalanceIsNotNull("%user%")
        log.info "countByNameIlikeAndBalanceIsNotNull : ${count2}"
        Integer count3 = User.countByNameIlikeOrBalanceInList("%user%", [1000, 2000, 3000])
        log.info "countByNameIlikeOrBalanceInList : ${count3}"
    }



    def destroy = {
    }
}
