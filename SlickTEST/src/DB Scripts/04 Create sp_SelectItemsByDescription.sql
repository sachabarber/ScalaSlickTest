USE [SLICKTEST]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_SelectItemsByDescription]
    (
      @description NVARCHAR(MAX)
    )
AS
BEGIN
	SET NOCOUNT ON;

	select * from Items i where i.[Description] LIKE '%' + @description + '%'

END

GO


