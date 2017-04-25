package gorm

class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        validate(users.first())
        users.each {
            findById(it.id)
        }
        users.each {
            getById(it.id)
        }
        findByProperty()
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

    void findById(Long id) {
        log.info "#######Finding User with Id :${id}##########"
        User user1 = User.findById(id)
        log.info "_______Finding User with Id :${id}___________"
        User user2 = User.findById(id)
        log.info "---------------------------------------------"
    }

    void getById(Long id) {
        log.info "#######Getting User with Id :${id}##########"
        User user1 = User.get(id)
        log.info "_______Getting User with Id :${id}___________"
        User user2 = User.get(id)
        log.info "---------------------------------------------"
    }

    void findByProperty() {
        User user = User.findByName("user 1")
        log.info "user---${user.id}---"
        User user1 = User.findByNameIlikeAndBalance("%user%", 5000)
        log.info "user1---${user1.id}---"
        User user2 = User.findByNameLikeOrBalance("%user%", 5000)
        log.info "user2---${user2.id}---"
        User user3 = User.findByNameIlikeOrBalance("%user%", 5000, [sort: 'balance', order: 'desc'])
        log.info "user3---${user3.id}---"
    }


    def destroy = {
    }
}
