package de.db12.common.db.mybatis;

import org.apache.ibatis.annotations.Select;

import de.db12.common.entity.Blog;

public interface BlogMapper {
	@Select("SELECT * FROM blog WHERE id = #{id}")
	Blog selectBlog(int id);
}