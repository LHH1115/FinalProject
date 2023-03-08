CREATE TABLE Tbl_Member (
    MemberNo NUMBER   NOT NULL,
    Name VARCHAR2(150)   NOT NULL,
    Jumin VARCHAR2(150)   NOT NULL,
    ID VARCHAR2(150)   NOT NULL,
    PWD VARCHAR2(150)   NOT NULL,
    Addr VARCHAR2(150)   NOT NULL,
    Email VARCHAR2(150)   NOT NULL,
    Phone VARCHAR2(150)   NOT NULL,
    Point NUMBER   NULL,
    Role VARCHAR2(100)   NOT NULL,
    MBTI VARCHAR2(150)   NULL,
    RouletteCount NUMBER   NOT NULL,
    Postcode VARCHAR2(100) NOT NULL,
    CONSTRAINT "pk_Tbl_Member" PRIMARY KEY (
        MemberNo
     )
);

CREATE TABLE Tbl_Intersts (
    InterestNo NUMBER   NOT NULL,
    MemberNo NUMBER   NOT NULL,
    Interest VARCHAR2(150)   NOT NULL,
    Score NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_Intersts" PRIMARY KEY (
        InterestNo
     )
);

CREATE TABLE Tbl_Accommodation (
    AccommoNo NUMBER   NOT NULL,
    Name VARCHAR2(150)   NOT NULL,
    Addr VARCHAR2(150)   NOT NULL,
    Phone VARCHAR2(150)   NOT NULL,
    Price NUMBER   NOT NULL,
    Category VARCHAR2(150)   NULL,
    CONSTRAINT "pk_Tbl_Accommodation" PRIMARY KEY (
        AccommoNo
     )
);

CREATE TABLE Tbl_AccommoPhoto (
    APNo NUMBER   NOT NULL,
    AccommoNo NUMBER   NOT NULL,
    Path VARCHAR2(150)   NOT NULL,
    Orders NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_AccommoPhoto" PRIMARY KEY (
        APNo
     )
);

CREATE TABLE Tbl_Reservation (
    Imp_uid VARCHAR2(100)   NOT NULL,
    MemberNo NUMBER   NOT NULL,
    AccommoNo NUMBER   NOT NULL,
    TotalPrice NUMBER   NOT NULL,
    Date_S DATE   NOT NULL,
    Date_E DATE   NOT NULL,
    Headcount NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_Reservation" PRIMARY KEY (
        Imp_uid
     )
);

CREATE TABLE Tbl_Inquiry (
    InquiryNo NUMBER   NOT NULL,
    MemberNo NUMBER   NOT NULL,
    Content VARCHAR2(150)   NOT NULL,
    Category VARCHAR2(150)   NOT NULL,
    InqDate DATE   NOT NULL,
    Title VARCHAR2(150) NOT NULL,
    ReplyOk NUMBER NULL,
    CONSTRAINT "pk_Tbl_Inquiry" PRIMARY KEY (
        InquiryNo
     )
);

CREATE TABLE Tbl_Reply (
    ReplyNo NUMBER   NOT NULL,
    InquiryNo NUMBER   NOT NULL,
    Content VARCHAR2(150)   NOT NULL,
    RepDate DATE   NOT NULL,
    CONSTRAINT "pk_Tbl_Reply" PRIMARY KEY (
        ReplyNo
     )
);

CREATE TABLE Tbl_Like (
    LikeNo NUMBER   NOT NULL,
    MemberNo NUMBER   NOT NULL,
    Category VARCHAR2(150)   NOT NULL,
    RefNo NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_Like" PRIMARY KEY (
        LikeNo
     )
);

CREATE TABLE Tbl_Review (
    ReviewNo NUMBER   NOT NULL,
    MemberNo NUMBER   NOT NULL,
    Title VARCHAR2(150)   NOT NULL,
    AccommoNo NUMBER   NOT NULL,
    Content VARCHAR2(150)   NULL,
    Stars NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_Review" PRIMARY KEY (
        ReviewNo
     )
);

CREATE TABLE Tbl_Event (
    EventNo NUMBER   NOT NULL,
    MemberNo NUMBER   NOT NULL,
    EventDATE DATE   NOT NULL,
    Category VARCHAR2(150)   NOT NULL,
    Amount NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_Event" PRIMARY KEY (
        EventNo
     )
);


CREATE TABLE Tbl_Restaurant (
    RestauNo NUMBER   NOT NULL,
    Name VARCHAR2(150)   NOT NULL,
    Addr VARCHAR2(150)   NULL,
    Phone VARCHAR2(150)   NULL,
    Category VARCHAR2(150)   NULL,
    CONSTRAINT "pk_Tbl_Restaurant" PRIMARY KEY (
        RestauNo
     )
);

CREATE TABLE Tbl_RestaurantPhoto (
    RPNo NUMBER   NOT NULL,
    RestauNo NUMBER   NOT NULL,
    Path VARCHAR2(150)   NOT NULL,
    Orders NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_RestaurantPhoto" PRIMARY KEY (
        RPNo
     )
);

CREATE TABLE Tbl_Attraction (
    AttractNo NUMBER   NOT NULL,
    Name VARCHAR2(150)   NOT NULL,
    Addr VARCHAR2(150)   NULL,
    Phone VARCHAR2(150)   NULL,
    Category VARCHAR2(150)   NULL,
    CONSTRAINT "pk_Tbl_Attraction" PRIMARY KEY (
        AttractNo
     )
);

CREATE TABLE Tbl_AttractInfo (
    ATINo NUMBER   NOT NULL,
    AttractNo NUMBER   NOT NULL,
    Info VARCHAR2(700)   NOT NULL,
    Orders NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_AttractInfo" PRIMARY KEY (
        ATINo
     )
);

CREATE TABLE Tbl_AttractPhoto (
    APNo NUMBER   NOT NULL,
    AttractNo NUMBER   NOT NULL,
    Path VARCHAR2(150)   NOT NULL,
    Orders NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_AttractPhoto" PRIMARY KEY (
        APNo
     )
);

CREATE TABLE Tbl_Car (
    CarNo NUMBER   NOT NULL,
    ModelName VARCHAR2(150)   NULL,
    PhotoPath VARCHAR2(150)   NULL,
    Category VARCHAR2(150)   NULL,
    Price NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_Car" PRIMARY KEY (
        CarNo
     )
);

CREATE TABLE Tbl_RentCar (
    RentNo NUMBER   NOT NULL,
    Name VARCHAR2(150)   NOT NULL,
    Addr VARCHAR2(150)   NULL,
    Phone VARCHAR2(150)   NULL,
    CarNo NUMBER   NOT NULL,
    Stars NUMBER   NOT NULL,
    CONSTRAINT "pk_Tbl_RentCar" PRIMARY KEY (
        RentNo
     )
);


ALTER TABLE Tbl_Intersts ADD CONSTRAINT "fk_Tbl_Intersts_MemberNo" FOREIGN KEY(MemberNo)
REFERENCES Tbl_Member (MemberNo)ON DELETE CASCADE;

ALTER TABLE Tbl_AccommoPhoto ADD CONSTRAINT "fk_Tbl_AccommoPhoto_AccommoNo" FOREIGN KEY(AccommoNo)
REFERENCES Tbl_Accommodation (AccommoNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Reservation ADD CONSTRAINT "fk_Tbl_Reservation_MemberNo" FOREIGN KEY(MemberNo)
REFERENCES Tbl_Member (MemberNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Reservation ADD CONSTRAINT "fk_Tbl_Reservation_AccommoNo" FOREIGN KEY(AccommoNo)
REFERENCES Tbl_Accommodation (AccommoNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Inquiry ADD CONSTRAINT "fk_Tbl_Inquiry_MemberNo" FOREIGN KEY(MemberNo)
REFERENCES Tbl_Member (MemberNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Reply ADD CONSTRAINT "fk_Tbl_Reply_InquiryNo" FOREIGN KEY(InquiryNo)
REFERENCES Tbl_Inquiry (InquiryNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Like ADD CONSTRAINT "fk_Tbl_Like_MemberNo" FOREIGN KEY(MemberNo)
REFERENCES Tbl_Member (MemberNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Review ADD CONSTRAINT "fk_Tbl_Review_MemberNo" FOREIGN KEY(MemberNo)
REFERENCES Tbl_Member (MemberNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Review ADD CONSTRAINT "fk_Tbl_Review_AccommoNo" FOREIGN KEY(AccommoNo)
REFERENCES Tbl_Accommodation (AccommoNo)ON DELETE CASCADE;

ALTER TABLE Tbl_Event ADD CONSTRAINT "fk_Tbl_Event_MemberNo" FOREIGN KEY(MemberNo)
REFERENCES Tbl_Member (MemberNo)ON DELETE CASCADE;

ALTER TABLE Tbl_RestaurantPhoto ADD CONSTRAINT "fk_Tbl_RestaurantPhoto_RestauNo" FOREIGN KEY(RestauNo)
REFERENCES Tbl_Restaurant (RestauNo)ON DELETE CASCADE;

ALTER TABLE Tbl_AttractInfo ADD CONSTRAINT "fk_Tbl_AttractInfo_AttractNo" FOREIGN KEY(AttractNo)
REFERENCES Tbl_Attraction (AttractNo)ON DELETE CASCADE;

ALTER TABLE Tbl_AttractPhoto ADD CONSTRAINT "fk_Tbl_AttractPhoto_AttractNo" FOREIGN KEY(AttractNo)
REFERENCES Tbl_Attraction (AttractNo)ON DELETE CASCADE;

ALTER TABLE Tbl_RentCar ADD CONSTRAINT "fk_Tbl_RentCar_CarNo" FOREIGN KEY(CarNo)
REFERENCES Tbl_Car (CarNo)ON DELETE CASCADE;

