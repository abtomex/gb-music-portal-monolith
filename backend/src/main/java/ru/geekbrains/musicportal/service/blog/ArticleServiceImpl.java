package ru.geekbrains.musicportal.service.blog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.musicportal.entity.blog.Article;
import ru.geekbrains.musicportal.repository.ArticleRepository;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public void changeTitleById(Long id, String newTitle) {
        Optional<Article> optional = findById(id);
        if (optional.isPresent()) {
            Article article = optional.get();
            article.setTitle(newTitle);
            save(article);
        }
    }

    public void addContent(Long id, String content) {
        Optional<Article> optional = findById(id);
        if (optional.isPresent()) {
            Article article = optional.get();
            article.setContent(content);
            save(article);
        }
    }

    @Override
    public Article save(Article entity) {
        return articleRepository.save(entity);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }
}
