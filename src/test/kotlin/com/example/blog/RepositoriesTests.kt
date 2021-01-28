package com.example.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository){

    @Test
    fun `When findByIdOrNull then return Article`() {
        val jurgen = User("springjurgen","Juergen", "Hoeller")
        entityManager.persist(jurgen)
        val article = Article("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", jurgen)
        entityManager.persist(article)
        entityManager.flush()
        val found = articleRepository.findByIdOrNull(article.id!!)

        assertThat(found).isEqualTo(article)
        println("id of article ${found?.id}")
    }

    @Test
    internal fun `When findByLogin then return User`() {
        val jurgen = User("springjurgen","Juergen", "Hoeller")
        entityManager.persist(jurgen)
        entityManager.flush()
        val user = userRepository.findByLogin(jurgen.login)

        assertThat(user).isEqualTo(jurgen)
    }
}

