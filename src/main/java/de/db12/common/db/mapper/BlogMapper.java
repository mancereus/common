package de.db12.common.db.mapper;

import org.apache.ibatis.annotations.Select;

import de.db12.common.db.entity.Blog;

public interface BlogMapper {
	@Select("SELECT * FROM blog WHERE id = #{id}")
	Blog selectBlog(int id);
}