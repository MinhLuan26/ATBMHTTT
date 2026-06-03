USE [master]
GO
CREATE DATABASE [OldBookStoreDB]
GO
ALTER DATABASE [OldBookStoreDB] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [OldBookStoreDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [OldBookStoreDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [OldBookStoreDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [OldBookStoreDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [OldBookStoreDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [OldBookStoreDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [OldBookStoreDB] SET  MULTI_USER 
GO
ALTER DATABASE [OldBookStoreDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [OldBookStoreDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [OldBookStoreDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [OldBookStoreDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [OldBookStoreDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [OldBookStoreDB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [OldBookStoreDB] SET QUERY_STORE = ON
GO
ALTER DATABASE [OldBookStoreDB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [OldBookStoreDB]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[books](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](255) NOT NULL,
	[author] [nvarchar](255) NOT NULL,
	[price] [decimal](18, 2) NOT NULL,
	[description] [nvarchar](max) NULL,
	[image_url] [varchar](max) NULL,
	[condition] [nvarchar](50) NULL,
	[status] [varchar](20) NULL,
	[seller_id] [int] NOT NULL,
	[created_at] [datetime] NULL,
	[category_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart_items](
	[cart_item_id] [int] IDENTITY(1,1) NOT NULL,
	[cart_id] [int] NOT NULL,
	[book_id] [int] NOT NULL,
	[quantity] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[cart_item_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[carts](
	[cart_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cart_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[categories](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_details](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NOT NULL,
	[book_id] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [decimal](18, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[total_price] [decimal](18, 2) NOT NULL,
	[status] [varchar](20) NULL,
	[order_date] [datetime] NULL,
	[shipping_address] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[reviews](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[book_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
	[rating] [int] NULL,
	[comment] [nvarchar](max) NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fullname] [nvarchar](100) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[phone] [varchar](20) NULL,
	[role] [varchar](20) NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[wishlist](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[book_id] [int] NOT NULL,
	[added_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[books] ON 

INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (3, N'Dế Mèn Phiêu Lưu Ký', N'Tô Hoài', CAST(45000.00 AS Decimal(18, 2)), N'Tác phẩm thiếu nhi kinh điển của Việt Nam', N'https://nhasachmienphi.com/images/thumbnail/nhasachmienphi-de-men-phieu-luu-ky.jpg', N'USED', N'ACTIVE', 14, CAST(N'2025-12-13T23:02:18.637' AS DateTime), 1)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (4, N'Hoàng Tử Bé', N'Antoine de Saint-Exupéry', CAST(60000.00 AS Decimal(18, 2)), N'Truyện thiếu nhi nổi tiếng thế giới', N'https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1483156248i/12617037.jpg', N'NEW', N'ACTIVE', 14, CAST(N'2025-12-13T23:02:18.637' AS DateTime), 1)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (5, N'Java Cơ Bản', N'Huỳnh Công Pháp', CAST(120000.00 AS Decimal(18, 2)), N'Sách học Java từ cơ bản đến nâng cao', N'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1629202926i/43230197.jpg', N'NEW', N'ACTIVE', 14, CAST(N'2025-12-13T23:02:18.637' AS DateTime), 2)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (6, N'Lập Trình Web Với Servlet & JSP', N'Hoàng Dức Hải', CAST(150000.00 AS Decimal(18, 2)), N'Hướng dẫn chi tiết Servlet/JSP cho sinh viên', N'https://xemsachhay.com/wp-content/uploads/2018/04/6252_12149.jpg', N'NEW', N'ACTIVE', 14, CAST(N'2025-12-13T23:02:18.637' AS DateTime), 2)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (7, N'Cha Giàu Cha Nghèo', N'Robert Kiyosaki', CAST(90000.00 AS Decimal(18, 2)), N'Sách tài chính cá nhân nổi tiếng', N'https://laz-img-sg.alicdn.com/p/4cfe63663e74cb70877ac94ce4003984.jpg', N'USED', N'ACTIVE', 14, CAST(N'2025-12-13T23:02:18.637' AS DateTime), 3)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (8, N'Nhà Giả Kim', N'Paulo Coelho', CAST(80000.00 AS Decimal(18, 2)), N'Tiểu thuyết truyền cảm hứng', N'https://toplist.vn/images/800px/nha-gia-kim-paulo-coelho-4777.jpg', N'NEW', N'ACTIVE', 14, CAST(N'2025-12-13T23:02:18.637' AS DateTime), 4)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (9, N'1001 cách giữ chân khách hàng', N'Nhật Ly', CAST(80000.00 AS Decimal(18, 2)), N'Bạn sẽ tìm thấy 1001 đáp án trong việc làm thế nào để thiết lập được mối quan hệ gắn bó với khách hàng. Những đáp án này đều được rút ra từ kết quả thực tiễn sinh động. Bí quyết kinh doanh không thể tìm thấy ở đâu khác, những ý tưởng mới mẻ đều ở trong cuốn sách này!', N'https://nhasachmienphi.com/wp-content/uploads/1001-cach-giu-chan-khach-hang.jpg', N'NEW', N'ACTIVE', 14, CAST(N'2025-12-14T23:19:11.990' AS DateTime), 3)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (10, N'Kinh Nghiệm Thành Công Của Ông Chủ Nhỏ', N'Lão Mạc', CAST(65000.00 AS Decimal(18, 2)), N'Kinh Nghiệm Thành Công Của Ông Chủ Nhỏ là một cuốn sách có nội dung khác biệt với những cuốn sách kinh doanh thông thường khác, có thể sẽ giúp ích được cho những bạn trẻ đã và đang dấn thân vào lĩnh vực kinh doanh. Trong cuốn sách này, trước tiên, tác giả nêu ra tình huống, sau đó đi sâu phân tích, đưa ra những luận điểm về những tình tiết quan trọng trong câu chuyện và đề cập tới những lĩnh vực có thể ứng dụng luận điểm đó. Những câu chuyện mà tác giả viết ra không được phân loại theo phương thức quản lí kinh doanh mà dựa theo đặc điểm cách làm của doanh nghiệp. Và đặc biệt, đó không phải là những câu chuyện quen thuộc từng được đề cập trong giáo trình thương mại, mà phần lớn là những điều tâm đắc và những trải nghiệm của chính tác giả.', N'https://nhasachmienphi.com/images/thumbnail/nhasachmienphi-kinh-nghiem-thanh-cong-cua-ong-chu-nho.jpg', N'NEW', N'ACTIVE', 14, CAST(N'2025-12-14T23:19:11.990' AS DateTime), 3)
INSERT [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [created_at], [category_id]) VALUES (11, N'Trảm Long – Trọn Bộ 4 Tập', N'Hồng Trần', CAST(125000.00 AS Decimal(18, 2)), N'Đệ nhất kỳ thư về phong thủy trong thiên hạ, LONG QUYẾT đã thất lạc cả nghìn năm trước, nay lại thấp thoáng ẩn hiện trong nhân gian. Tầm long quyết, tìm khắp long mạch trong thiên hạ.Ngự long quyết, vận dụng long khí, xoay trời chuyển đất.Trảm long quyết, cắt đứt long mạch, triệt tiêu long khí. Đầu mối duy nhất để tìm Long Quyết là Lục Kiều Kiều, cô gái xinh đẹp sinh sống ở Quảng Châu, chuyên nghề xem bói cho kỹ nữ. Dưới sự sắp đặt của Quốc sư thần bí, cô đã từ bỏ cuộc sống thanh bình ẩn dật, cùng người tình ngoại quốc và chú nhóc đệ tử mới thu nhận bước lên con đường đào vong, giữ kín bí mật lớn của gia tộc.Vậy nhưng, triều đình Đại Thanh đang lúc đối mặt nguy cơ diệt vong sẽ bỏ qua cuốn sách này chăng?', N'https://nhasachmienphi.com/images/thumbnail/nhasachmienphi-tram-long-tron-bo-4-tap.jpg', N'USED', N'ACTIVE', 14, CAST(N'2025-12-14T23:19:11.990' AS DateTime), 4)
SET IDENTITY_INSERT [dbo].[books] OFF
GO
SET IDENTITY_INSERT [dbo].[cart_items] ON 

INSERT [dbo].[cart_items] ([cart_item_id], [cart_id], [book_id], [quantity]) VALUES (1, 1, 7, 2)
INSERT [dbo].[cart_items] ([cart_item_id], [cart_id], [book_id], [quantity]) VALUES (10, 2, 11, 2)
SET IDENTITY_INSERT [dbo].[cart_items] OFF
GO
SET IDENTITY_INSERT [dbo].[carts] ON 

INSERT [dbo].[carts] ([cart_id], [user_id]) VALUES (1, 3)
INSERT [dbo].[carts] ([cart_id], [user_id]) VALUES (2, 15)
SET IDENTITY_INSERT [dbo].[carts] OFF
GO
SET IDENTITY_INSERT [dbo].[categories] ON 

INSERT [dbo].[categories] ([id], [name]) VALUES (1, N'Thiếu nhi')
INSERT [dbo].[categories] ([id], [name]) VALUES (2, N'Lập trình')
INSERT [dbo].[categories] ([id], [name]) VALUES (3, N'Kinh tế')
INSERT [dbo].[categories] ([id], [name]) VALUES (4, N'Tiểu thuyết')
SET IDENTITY_INSERT [dbo].[categories] OFF
GO
SET IDENTITY_INSERT [dbo].[users] ON 

INSERT [dbo].[users] ([id], [fullname], [email], [password], [phone], [role], [created_at]) VALUES (3, N'TranKhang-Tr', N'tkhang.tphu@gmail.com', N'$2a$12$iv//uK4l2a/D2dM51mygxeOLh1kZ8mfnZb.waUtnHZ7W18TSvZdhm', N'0389686085', N'BUYER', CAST(N'2025-12-12T17:21:07.900' AS DateTime))
INSERT [dbo].[users] ([id], [fullname], [email], [password], [phone], [role], [created_at]) VALUES (12, N'Tam Luc Gia', N'tkhang2.tphu@gmail.com', N'$2a$10$4Dcu0Mx9d1tffOGK2WVOau/5YJ1pC1XP57mZf..OXcUHTUwXdFF0G', N'1332431', N'BUYER', CAST(N'2025-12-13T20:43:17.790' AS DateTime))
INSERT [dbo].[users] ([id], [fullname], [email], [password], [phone], [role], [created_at]) VALUES (13, N'ADMIN', N'admin@bookstore.com', N'$2a$12$Eml0hCgAxppmeU7mId/y7eNx6e/6FVjqYtqqe3R7PeF9O/HuNv5Wi', N'0980011234', N'ADMIN', CAST(N'2025-12-13T21:04:28.580' AS DateTime))
INSERT [dbo].[users] ([id], [fullname], [email], [password], [phone], [role], [created_at]) VALUES (14, N'Nhi Duong Gia', N'tkhang1.tphu@gmail.com', N'$2a$12$U0x3YDShKS6eZ2KNwswKKuYNcFJCYZmUH62AD0nP4B0w0mvDyt8J6', N'12353456', N'SELLER', CAST(N'2025-12-13T21:09:25.250' AS DateTime))
INSERT [dbo].[users] ([id], [fullname], [email], [password], [phone], [role], [created_at]) VALUES (15, N'Test_Dang_Ky', N'test@gmail.com', N'$2a$12$G9VNnIdNYaqt753YduVdee8P.kC8pTZCAwsRjgWJPfDrLdlRD3DyW', N'0123456789', N'BUYER', CAST(N'2025-12-14T20:39:33.940' AS DateTime))
SET IDENTITY_INSERT [dbo].[users] OFF
GO
ALTER TABLE [dbo].[cart_items] ADD  CONSTRAINT [UQ_cart_book] UNIQUE NONCLUSTERED 
(
	[cart_id] ASC,
	[book_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[carts] ADD UNIQUE NONCLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[wishlist] ADD  CONSTRAINT [UQ_wishlist_user_book] UNIQUE NONCLUSTERED 
(
	[user_id] ASC,
	[book_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[books] ADD  DEFAULT ('PENDING') FOR [status]
GO
ALTER TABLE [dbo].[books] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[orders] ADD  DEFAULT ('PENDING') FOR [status]
GO
ALTER TABLE [dbo].[orders] ADD  DEFAULT (getdate()) FOR [order_date]
GO
ALTER TABLE [dbo].[reviews] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ('BUYER') FOR [role]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[wishlist] ADD  DEFAULT (getdate()) FOR [added_at]
GO
ALTER TABLE [dbo].[books]  WITH CHECK ADD FOREIGN KEY([seller_id])
REFERENCES [dbo].[users] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[books]  WITH CHECK ADD  CONSTRAINT [FK_books_category] FOREIGN KEY([category_id])
REFERENCES [dbo].[categories] ([id])
GO
ALTER TABLE [dbo].[books] CHECK CONSTRAINT [FK_books_category]
GO
ALTER TABLE [dbo].[cart_items]  WITH CHECK ADD  CONSTRAINT [FK_item_book] FOREIGN KEY([book_id])
REFERENCES [dbo].[books] ([id])
GO
ALTER TABLE [dbo].[cart_items] CHECK CONSTRAINT [FK_item_book]
GO
ALTER TABLE [dbo].[cart_items]  WITH CHECK ADD  CONSTRAINT [FK_item_cart] FOREIGN KEY([cart_id])
REFERENCES [dbo].[carts] ([cart_id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[cart_items] CHECK CONSTRAINT [FK_item_cart]
GO
ALTER TABLE [dbo].[carts]  WITH CHECK ADD  CONSTRAINT [FK_cart_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[carts] CHECK CONSTRAINT [FK_cart_user]
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([book_id])
REFERENCES [dbo].[books] ([id])
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[reviews]  WITH CHECK ADD  CONSTRAINT [FK_reviews_book] FOREIGN KEY([book_id])
REFERENCES [dbo].[books] ([id])
GO
ALTER TABLE [dbo].[reviews] CHECK CONSTRAINT [FK_reviews_book]
GO
ALTER TABLE [dbo].[reviews]  WITH CHECK ADD  CONSTRAINT [FK_reviews_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[reviews] CHECK CONSTRAINT [FK_reviews_user]
GO
ALTER TABLE [dbo].[wishlist]  WITH CHECK ADD  CONSTRAINT [FK_wishlist_book] FOREIGN KEY([book_id])
REFERENCES [dbo].[books] ([id])
GO
ALTER TABLE [dbo].[wishlist] CHECK CONSTRAINT [FK_wishlist_book]
GO
ALTER TABLE [dbo].[wishlist]  WITH CHECK ADD  CONSTRAINT [FK_wishlist_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[wishlist] CHECK CONSTRAINT [FK_wishlist_user]
GO
ALTER TABLE [dbo].[cart_items]  WITH CHECK ADD CHECK  (([quantity]>(0)))
GO
ALTER TABLE [dbo].[reviews]  WITH CHECK ADD CHECK  (([rating]>=(1) AND [rating]<=(5)))
GO
USE [master]
GO
ALTER DATABASE [OldBookStoreDB] SET  READ_WRITE 
GO
SET IDENTITY_INSERT [dbo].[books] ON;

INSERT INTO [dbo].[books] ([id], [title], [author], [price], [description], [image_url], [condition], [status], [seller_id], [category_id]) 
VALUES 
(12, N'Đắc Nhân Tâm', N'Dale Carnegie', 85000.00, N'Cuốn sách thay đổi tư duy và cách đối nhân xử thế của hàng triệu người.', 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400', 'NEW', 'ACTIVE', 14, 3),
(13, N'Đời Ngắn Đừng Ngủ Dài', N'Robin Sharma', 65000.00, N'Lời khuyên giá trị để sống một cuộc đời ý nghĩa và hạnh phúc.', 'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400', 'USED', 'ACTIVE', 14, 3),
(14, N'Harry Potter và Hòn Đá Phù Thủy', N'J.K. Rowling', 120000.00, N'Khởi đầu của một hành trình ma thuật kỳ diệu.', 'https://images.unsplash.com/photo-1600189261179-994939a3f2d2?w=400', 'NEW', 'ACTIVE', 14, 1),
(15, N'Học Python Trong 7 Ngày', N'Nguyễn Văn A', 135000.00, N'Cẩm nang dành cho người mới bắt đầu lập trình.', 'https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=400', 'NEW', 'ACTIVE', 14, 2);
SET IDENTITY_INSERT [dbo].[books] OFF;

INSERT INTO [dbo].[categories] ([name]) VALUES (N'Truyện tranh'), (N'Sách ngoại ngữ'), (N'Tâm lý - Kỹ năng sống');
INSERT INTO [dbo].[reviews] ([book_id], [user_id], [rating], [comment]) 
VALUES 
(12, 3, 5, N'Cuốn sách rất hay, đáng để đọc!'),
(12, 15, 4, N'Nội dung rất ý nghĩa, bìa sách hơi cũ một chút nhưng không sao.'),
(15, 3, 5, N'Sách viết rất dễ hiểu cho người mới bắt đầu.');
SELECT count(*) FROM books;
UPDATE [dbo].[books] SET [status] = 'ACTIVE';
GO
UPDATE books SET status = 'ACTIVE';
GO
SELECT * FROM [dbo].[orders];
SELECT * FROM [dbo].[order_details];
GO
ALTER TABLE [dbo].[users] ADD public_key VARCHAR(MAX) NULL;
UPDATE users SET public_key = NULL WHERE email = 'luantun20@gmail.com';
ALTER TABLE users ADD key_status VARCHAR(20) DEFAULT 'ACTIVE';
ALTER TABLE users ADD key_updated_at DATETIME;