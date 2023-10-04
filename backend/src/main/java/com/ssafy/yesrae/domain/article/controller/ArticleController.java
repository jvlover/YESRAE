package com.ssafy.yesrae.domain.article.controller;

import com.ssafy.yesrae.common.exception.FileIOException;
import com.ssafy.yesrae.common.exception.NotFoundException;
import com.ssafy.yesrae.common.exception.Template.TemplateNoResultException;
import com.ssafy.yesrae.common.exception.Template.TemplatePossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.article.dto.request.ArticleDeletePutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleFindCondition;
import com.ssafy.yesrae.domain.article.dto.request.ArticleModifyPutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.dto.response.ArticleFindRes;
import com.ssafy.yesrae.domain.article.entity.Article;
import com.ssafy.yesrae.domain.article.service.ArticleService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    private static final String SUCCESS = "success"; // API 성공 시 return

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Article 등록을 위한 API
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> registArticle(@RequestPart ArticleRegistPostReq articleRegistPostReq,
        @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        if (files != null) { // 게시물에 파일 있으면
            log.info("ArticleController_regist_start: " + articleRegistPostReq.toString() + ", "
                + files);
        } else {
            log.info("ArticleController_regist_start: " + articleRegistPostReq.toString());
        }

        Article article = articleService.registArticle(articleRegistPostReq, files);
        if (article != null) {  // regist 성공하면 success
            log.info("ArticleController_regist_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 실패하면 Exception
            throw new FileIOException();
        }
    }

    /**
     * 게시글 삭제하기 위한 API
     *
     * @param articleDeletePutReq : 게시글 삭제
     */
    @PutMapping("/delete")
    public CommonResponse<?> deleteArticle(@PathVariable ArticleDeletePutReq articleDeletePutReq) {

        log.info("ArticleController_delete_start: " + articleDeletePutReq);

        boolean isDeleted = articleService.deleteArticle(articleDeletePutReq);

        if (isDeleted) {    // 삭제 성공하면 success
            log.info("ArticleController_delete_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 삭제 실패하면 Exception
            throw new TemplatePossessionFailException();
        }
    }

    /**
     * 유저가 게시글의 상세 정보를 확인하기 위한 API
     *
     * @param Id
     */
    @GetMapping("/{Id}")
    public CommonResponse<?> findArticle(@PathVariable Long Id) {

        log.info("ArticleController_find_start: " + Id);

        ArticleFindRes articleFindRes = articleService.findArticle(Id);

        if (articleFindRes != null) { // 조회 성공하면 조회 결과 return
            log.info("ArticleController_find_end: " + articleFindRes.toString());
            return CommonResponse.success(articleFindRes);
        } else {    // 조회 실패하면 Exception
            throw new NotFoundException();
        }
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> modifyArticle(@RequestPart ArticleModifyPutReq articleModifyPutReq,
        @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        if (files != null) {
            log.info("ArticleController_modify_start: " + articleModifyPutReq.toString() + ", "
                + files);
        } else {
            log.info("ArticleController_modify_start: " + articleModifyPutReq.toString());
        }
        boolean isModified = articleService.modifyArticle(articleModifyPutReq, files);

        if (isModified) {   // 수정 성공하면 success
            log.info("ArticleController_modify_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 수정 실패하면 Exception
            throw new TemplatePossessionFailException();
        }
    }

    /**
     * Article List 조회
     *
     * @return
     */
    @GetMapping()
    public CommonResponse<List<ArticleFindRes>> findAllArticle() {
        log.info("ArticleController_findAllArticle_start: ");
        Optional<List<ArticleFindRes>> findRes = Optional.ofNullable(
            articleService.findAllArticle());

        log.info("ArticleController_findAllArticle_end: " + findRes);
        return CommonResponse.success(findRes.orElseThrow(TemplateNoResultException::new));
    }

    /**
     * Article List 조회 (By keyword and categoryId)
     *
     * @return
     */
    @GetMapping("/find")
    public CommonResponse<List<ArticleFindRes>> findArticleByTitleAndCategory(ArticleFindCondition articleFindCondition) {
        log.info("ArticleController_findArticleByTitleAndCategory_start: ");
        Optional<List<ArticleFindRes>> findRes = Optional.ofNullable(
                articleService.findArticleByTitleAndCategory(articleFindCondition));

        log.info("ArticleController_findArticleByTitleAndCategory_end: " + findRes);
        return CommonResponse.success(findRes.orElseThrow(TemplateNoResultException::new));
    }
}
