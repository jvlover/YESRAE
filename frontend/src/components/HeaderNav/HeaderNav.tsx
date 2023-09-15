import React from 'react';
import { useRecoilValue } from 'recoil';
import { userState } from '../../recoil/user/user';
import ButtonComponent from '../common/ButtonComponent';
import { Link } from 'react-router-dom';

export default function HeaderNav() {
  const user = useRecoilValue(userState);

  return (
    <div className="fixed h-full w-64 bg-black">
      <div className="text-2xl px-8">YESRAE</div>
      {user.name ? (
        <>
          <div>
            <img
              src={user.imgUrl}
              alt={user.name}
              className="w-12 h-12 rounded-full"
            />
          </div>
          <div>{user.name}님 안녕하세요.</div>
        </>
      ) : (
        <Link to="/user/login">
          <ButtonComponent type="ismiddle">로그인</ButtonComponent>
        </Link>
      )}
      <ul className="text-white px-8 pt-4">
        <li className="text-xl pt-1">게시판</li>
        <li className="text-xl pt-1">플레이리스트</li>
        <li className="text-xl pt-1">이상형월드컵</li>
        <li className="text-xl pt-1">데일리퀴즈</li>
        <li className="text-xl pt-1">마이페이지</li>
      </ul>
    </div>
  );
}
