import React from 'react';

interface IButtonProps {
  type?: 'isSmall' | 'isBig' | 'istiny' | 'ismiddle';
  onClick?: () => any;
  children: React.ReactNode;
}

export default function ButtonComponent({
  type,
  onClick,
  children,
}: IButtonProps) {
  let buttonCss: string;
  let textCss: string;
  let margin = '';

  if (type === 'isBig') {
    buttonCss =
      'rounded-xl w-96 h-8 bg-gradient-to-r from-yesrae-0 to-yesrae-100';
    textCss = 'font-kdisplay text-xl';
    margin = 'm-1';
  } else if (type === 'istiny') {
    buttonCss =
      'rounded w-28 h-8 border-2 bg-gradient-to-r from-yesrae-0 to-yesrae-100 text-xl';
    textCss = 'font-kdisplay';
  } else if (type === 'ismiddle') {
    buttonCss =
      'rounded w-48 h-10 bg-gradient-to-r from-yesrae-0 to-yesrae-100 text-xl';
    textCss = 'font-kdisplay';
  } else {
    buttonCss =
      'rounded-xl w-64 h-8 bg-gradient-to-r from-yesrae-0 to-yesrae-100 text-2xl';
    textCss = 'font-kdisplay text-lg';
  }

  return (
    <div className={`flex justify-center ${margin}`}>
      <button type="button" className={buttonCss} onClick={onClick}>
        <div className={textCss}>{children}</div>
      </button>
    </div>
  );
}
