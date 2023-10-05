import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import { userState } from '../../recoil/user/user';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import { currentPageState, isListState } from '../../recoil/currentpage/currentPage';
import ButtonComponent from '../../components/common/ButtonComponent';
import { currentSongListState, currentSongState } from '../../recoil/currentsong/currentSong';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsisH, faHeart } from '@fortawesome/free-solid-svg-icons';
import { useParams } from 'react-router-dom';
import { registSongLike, songDetail } from '../../api/songApi.ts';

export interface ISongDetail {
  id: string,
  name: string,
  albumId: string,
  albumName: string,
  artistId: string,
  artistName: string,
  imgUrl: string,
  previewUrl: string,
  releaseYear: number,
  duration: number,
  popularity: number,
  acousticness: number,
  danceability: number,
  energy: number,
  instrumentalness: number,
  tune: number,
  liveness: number,
  loudness: number,
  mode: number,
  speechiness: number,
  tempo: number,
  timeSignature: number,
  valence: number,
  songlike: boolean,
}

export default function SongDetail() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정

  const user = useRecoilValue(userState);
  const { songId } = useParams();
  const setCurrentPage = useSetRecoilState(currentPageState);
  const [currentSong, setCurrentSong] = useRecoilState(currentSongState);
  const [isList, setIsList] = useRecoilState(isListState);
  const [songList, setSongList] = useRecoilState(currentSongListState);
  const [songLike, setSongLike] = useState(false);
  const [isTooltipVisible, setTooltipVisible] = useState(false);
  const [currentSongDetail, setCurrentSongDetail] = useState({
    id: '',
    name: '',
    albumId: '',
    albumName: '',
    artistId: '',
    artistName: '',
    imgUrl: '',
    previewUrl: '',
    releaseYear: 0,
    duration: 0,
    popularity: 0,
    acousticness: 0,
    danceability: 0,
    energy: 0,
    instrumentalness: 0,
    tune: 0,
    liveness: 0,
    loudness: 0,
    mode: 0,
    speechiness: 0,
    tempo: 0,
    timeSignature: 0,
    valence: 0,
    songlike: false,
  });
  const onChangePlayList = () => {
    // setCurrentPlayList
  };

  const onChangeSongDetail = async (data: Promise<ISongDetail>) => {
    setCurrentSongDetail(await data);
  };

  // const onChangeSong = (song: Song) => {
  //   setCurrentSong(song);
  // };

  const onChangeSonglike = () => {
    setSongLike(!songLike);
  };

  useEffect(() => {
    setCurrentPage({ pageName: '' });
  }, []);

  useEffect(() => {
    if (songId != null) {
      const res = songDetail(user.id, songId);
      if (res != null) {
        onChangeSongDetail(res);
        startLikeCheck();
      }
    }
  }, [currentSongDetail.songlike]);

  const toggleTooltip = () => {
    setTooltipVisible(!isTooltipVisible);
  };

  const SongLike = () => {
    onChangeSonglike();
    if (songId != null) {
      registSongLike(user.id, songId);
    }
  };

  const startLikeCheck = () => {
    if (currentSongDetail.songlike) {
      onChangeSonglike();
    }
  };

  return (
    <div>
      <div className='flex'>
        <div>
          <HeaderNav />
        </div>
        <main className='w-10/12 pr-20 pt-12 pl-72'>
          <div className='flex pb-10 border-b border-gray-900'>
            <img
              className='w-56 h-56'
              src={currentSongDetail.imgUrl}
            ></img>
            <div className='relative px-6'>
              <div className='text-3xl font-bold text-white'>
                {currentSongDetail.name}
              </div>
              <div className='pt-3 text-xl text-white'>
                <div className='inline'>
                  {currentSongDetail.artistName}
                </div>
              </div>
              <div className='pt-1 text-gray-500'>
                수록 앨범
                <div className='ml-3 inline'>
                  {currentSongDetail.albumName}
                </div>
              </div>
              <div className='pt-1 text-gray-500'>
                출시 연도
                <div className='ml-3 inline'>
                  {currentSongDetail.releaseYear}
                </div>
              </div>
              <div className='pt-1 text-gray-500'>
                {Math.floor(currentSongDetail.duration / 60000)}분 {Math.floor(currentSongDetail.duration % 60000 / 1000)}초
              </div>
              <div className='flex pt-3'>
                <ButtonComponent onClick={onChangePlayList} type='isSmall'>
                  재생하기
                </ButtonComponent>
                <button
                  className='flex items-center justify-center w-10 h-10 mx-4 bg-black border-2 border-gray-700 rounded-full group'
                  onClick={SongLike}>
                  {songLike ? <FontAwesomeIcon
                    icon={faHeart}
                    className='w-5 h-5 text-red-600'
                  /> : <FontAwesomeIcon
                    icon={faHeart}
                    className='group-hover:text-red-600 group-hover:w-5 group-hover:h-5'
                  />}
                </button>
                <button
                  className='flex items-center justify-center w-10 h-10 bg-black border-2 border-gray-700 rounded-full group '
                  onClick={toggleTooltip}
                >
                  <FontAwesomeIcon
                    icon={faEllipsisH}
                    className='w-5 h-5 group-hover:text-white'
                  />
                </button>
                {isTooltipVisible && (
                  <div
                    className='absolute z-20 w-48 py-2 mx-8 bg-gray-900 rounded-lg shadow-lg left-60'>
                    <div className='px-2 py-1 hover:bg-gray-800' onClick={onChangePlayList}>
                      💘 플레이 리스트에 추가
                    </div>
                    <div className='px-2 py-1 hover:bg-gray-800' onClick={SongLike}>🥰 좋아요
                    </div>
                    <div className='px-2 py-1 hover:bg-gray-800'>👩‍❤️‍👩 공유</div>
                  </div>
                )}
              </div>
            </div>
          </div>
          <div className='fixed relative bottom-0 left-0 h-52'>
            popularity : {currentSongDetail.popularity}<br /><br />
            acosticness : {currentSongDetail.acousticness}<br /><br />
            danceability : {currentSongDetail.danceability}<br /><br />
            energy : {currentSongDetail.energy}<br /><br />
            instrumentalness : {currentSongDetail.instrumentalness}<br /><br />
            livness : {currentSongDetail.liveness}<br /><br />
            loudness : {currentSongDetail.loudness}<br /><br />
            speechiness : {currentSongDetail.speechiness}<br /><br />
            tempo : {currentSongDetail.tempo}<br /><br />
            mode : {currentSongDetail.mode}<br /><br />
            tune : {currentSongDetail.tune}<br /><br />
            timeSignature : {currentSongDetail.timeSignature}<br /><br />
            valence : {currentSongDetail.valence}<br /><br />
          </div>
        </main>
      </div>
      <footer>
        <MusicPlayer />
      </footer>
    </div>
  );
}
