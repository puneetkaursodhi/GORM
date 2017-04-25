package gorm

class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        listRecords()
        listRecordsByIds(users.id)
        findAllRecords()
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

    void listRecords() {
        List<User> users = User.list([sort: 'dob', order: 'desc', max: 5, offset: 0])
        log.info "Listing records for list method"
        logRecords(users)
    }

    void listRecordsByIds(List<Long> ids) {
        List<User> users = User.getAll(ids)
        logRecords(users)
    }

    void findAllRecords() {
        List<User> users = User.findAllByName("user 1", [sort: 'dob', order: 'desc', max: 5, offset: 0])
        log.info "Listing records of findAllByName"
        logRecords(users)
        List<User> users1 = User.findAllByNameIlikeAndBalanceGreaterThan("%user%", 4000, [sort: 'dob', order: 'desc', max: 5, offset: 0])
        log.info "Listing records of findAllByNameIlikeAndBalanceGreaterThan"
        logRecords(users1)
        List<User> users2 = User.findAllByNameIlikeOrBalanceGreaterThan("%user%", 4000, [sort: 'dob', order: 'desc', max: 5, offset: 0])
        log.info "Listing records of findAllByNameIlikeOrBalanceGreaterThan"
        logRecords(users2)

    }

    void logRecords(List<User> users) {
        users.each { User user ->
            log.info "---${user.name}--${user.dob}"
        }
    }


    def destroy = {
    }
}
