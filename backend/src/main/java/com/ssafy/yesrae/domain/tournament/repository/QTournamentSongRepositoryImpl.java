package com.ssafy.yesrae.domain.tournament.repository;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.ssafy.yesrae.domain.tournament.entity.QTournamentSong.tournamentSong;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentPopularSongFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Querydsl을 위한 이상형 월드컵 Repository
 */
@Slf4j
@Repository
public class QTournamentSongRepositoryImpl implements QTournamentSongRepository{

    private final JPAQueryFactory queryFactory;

    public QTournamentSongRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 이상형 월드컵을 시작하기 위해 필요한 노래 조회 Query
     *
     * @param findTournamentSongGetReq : 라운드 수(8강, 16강, 32강, 64강, 128강, 256강)
     */
    @Override
    public List<TournamentSongFindRes> findTournamentSong(
        FindTournamentSongGetReq findTournamentSongGetReq) {

        log.info("QTournamentSongRepository_findTournamentSong_start: "
            + findTournamentSongGetReq.toString());

        return queryFactory
            .select(Projections.constructor(TournamentSongFindRes.class,
                tournamentSong.title.as("title"),
                tournamentSong.singer.as("singer"),
                tournamentSong.url.as("url")))
            .from(tournamentSong)
            .orderBy(Expressions.numberTemplate(Double.class, "RAND()").asc())
            .limit(findTournamentSongGetReq.getRound())
            .fetch();
    }

    /**
     * 이상형 월드컵 모든 유저들의 플레이 합한 결과 노래들을 1등 많이 한 순위로 정렬해서 가져오는 Query
     */
    @Override
    public List<TournamentPopularSongFindRes> findTournamentPopularSong() {

        log.info("QTournamentSongRepository_findTournamentPopularSong_start");

        return queryFactory
            .select(Projections.constructor(TournamentPopularSongFindRes.class,
                tournamentSong.title.as("title"),
                tournamentSong.singer.as("singer"),
                tournamentSong.vote.doubleValue()
                    .divide(tournamentSong.vote.sum().doubleValue()).as("proportion")))
            .from(tournamentSong)
            .orderBy(tournamentSong.vote.desc())
            .fetch();
    }
}
