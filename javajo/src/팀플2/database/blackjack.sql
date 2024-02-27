drop database if exists blackjack;

create database blackjack;

use blackjack;

create table member( 							# -------------------- 멤버 테이블 ---------------------
	mno int auto_increment primary key, 		# 멤버 번호
    mid varchar(20),							# 멤버 아이디
    mpw varchar(20),							# 멤버 비밀번호
    wins int default 0,							# 승리 수
    loses int default 0,						# 패배 수
    playcount int default 0						# 게임 수
    
);

create table room(                        # -------------------- 방 테이블 ----------------------
   rno int auto_increment primary key,         # 방 번호 
    nowpeople int,                        # 현재 인원 
    maxpeople int default 2,               # 최대 인원 
    stay int,                           # stay 횟수
    turn int,                            # 해당 턴   rdno와 mno를 활용 (멤버 테이블의 fk)
    host int,                           # 방장 (멤버 테이블의 fk)
    cardlist varchar(300),                  # 카드 리스트  
    foreign key(turn) references member(mno)  ON DELETE CASCADE,   # turn에 해당 턴의 mno 삽입
    foreign key(host) references member(mno) ON DELETE CASCADE   
);



create table roomdetails(                  # -------------------- 방 상세 테이블 ----------------------
   rdno int auto_increment primary key,      # 방 상세 번호
    rno int,                           # 방 번호 (방 테이블의 fk)
    mno int,                           # 멤버 번호 (멤버 테이블의 fk)
    rddatetime datetime default now(),         # 멤버 마지막 접속시간
    rdready int,                        # 준비 여부 
    holdcard varchar(2000) DEFAULT "",                  # 내 카드
    
    foreign key(rno) references room(rno)  ON DELETE CASCADE ,      
    foreign key(mno) references member(mno)   ON DELETE CASCADE
);
 
drop table if exists roomdetails;
drop table if exists roomdeck;
drop table if exists inventory;


