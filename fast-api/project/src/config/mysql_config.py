import os

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
from sqlalchemy.orm import Session

load_dotenv(verbose=True)

DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")
DB_HOST = os.getenv("DB_HOST")
DB_PORT = os.getenv("DB_PORT")


DATABASE_URL = f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/yesrae"

engine = create_engine(DATABASE_URL, echo = True)

SessionFactory = sessionmaker(autocommit = False, autoflush=False, bind=engine)


def get_db() -> Session:

    session = SessionFactory()

    try:
        yield session
    finally:
        session.close()
