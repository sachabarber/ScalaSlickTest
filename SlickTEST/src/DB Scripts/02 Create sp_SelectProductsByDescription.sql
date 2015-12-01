USE [SLICKTEST]
GO

/****** Object:  StoredProcedure [dbo].[sp_SelectProductsByDescription]    Script Date: 17/11/2015 4:12:46 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_SelectProductsByDescription]
    (
      @desc NVARCHAR(MAX)
    )
AS
BEGIN
	SET NOCOUNT ON;

	select * from Products p where p.[Description] LIKE '%' + @desc + '%'

END

GO


