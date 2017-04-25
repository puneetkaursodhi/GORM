package gorm

class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        validate(users.first())
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

    void validate(User user) {
        log.info "---Before value set HasErrors---${user.hasErrors()}"
        user.name = null
        log.info "---After value set HasErrors---${user.hasErrors()}"
        log.info "---User is valid :: ${user.validate()}"
        log.info "---User email is valid :: ${user.validate(['email'])}"
        log.info "---After validate HasErrors---${user.hasErrors()}"
        user.save(validate: false)
    }

    def destroy = {
    }
}
