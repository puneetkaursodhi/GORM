package gorm

class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        findCreate()
        findSave()
        findCreateSaveWhere()
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

    void findCreate() {
        User user = User.findOrCreateByName("user 1")
        log.info "user-----findOrCreateByName---${user.id}"
        User user1 = User.findOrCreateByName("user 11")
        log.info "user1-----findOrCreateByName---${user1.id}"

        User user2 = User.findOrCreateByNameAndBalance("user 1", 1000)
        log.info "user2-----findOrCreateByNameAndBalance---${user2.id}"
        User user3 = User.findOrCreateByNameAndBalance("user 1", 12000)
        log.info "user3-----findOrCreateByNameAndBalance---${user3.id}"
    }

    void findSave() {
        User user = User.findOrSaveByName("user 1")
        log.info "user-----findOrSaveByName---${user?.id}"
        User user1 = User.findOrSaveByName("user 11")
        log.info "user1-----findOrSaveByName---${user1?.id}"

        User user2 = User.findOrSaveByNameAndBalance("user 1", 1000)
        log.info "user2-----findOrSaveByNameAndBalance---${user2?.id}"
        User user3 = User.findOrSaveByNameAndBalance("user 1", 12000)
        log.info "user3-----findOrSaveByNameAndBalance---${user3?.id}"
    }

    void findCreateSaveWhere() {
        User user = User.findOrCreateWhere([name: 'user 1', email: 'user+1@gmail.com', balance: 1000])
        log.info "user-----findOrCreateWhere---${user?.id}"
        User user1 = User.findOrCreateWhere([name: 'user 1', email: 'user+1@gmail.com', balance: 10000])
        log.info "user1-----findOrCreateWhere---${user1?.id}"

        User user2 = User.findOrSaveWhere([name: 'user 1', email: 'user+1@gmail.com', balance: 1000])
        log.info "user2-----findOrSaveWhere---${user2?.id}"
        User user3 = User.findOrSaveWhere([name: 'user 1', email: 'user+1@gmail.com', balance: 21000])
        log.info "user3-----findOrSaveWhere---${user3?.id}"
    }


    def destroy = {
    }
}
