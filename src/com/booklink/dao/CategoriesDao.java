package com.booklink.dao;

import com.booklink.model.categories.Categories;
import com.booklink.model.categories.CategoryDto;
import com.booklink.model.categories.CategoryWithLevelDto;
import com.booklink.model.categories.ParentCategoryDto;
import com.booklink.utils.DBConnectionUtils;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriesDao {
    // 메인 카테고리를 insert 한다.
    public void insertMainCategories(CategoryDto categoryDto) {
        String sql = "{call categories_pkg.insert_maincategory(?)}";
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, categoryDto.name());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertSubCategories(CategoryDto categoryDto) {
        String sql = "{call categories_pkg.insert_subcategory(?, ?)}";
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, categoryDto.name());
            cstmt.setLong(2, categoryDto.priorId());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

    public List<CategoryWithLevelDto> allCategoriesWithLevel() {
        String sql = "call Categories_Pkg.find_all_with_level(?)";
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cstmt = null;
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(1);
            List<CategoryWithLevelDto> dtos = new ArrayList<>();
            while (rs.next()) {
                dtos.add(new CategoryWithLevelDto(rs.getLong("category_id"),
                                rs.getString("category_name"),
                                rs.getLong("prior_category_id"),
                                rs.getInt("t_depth")));
            }
            return dtos;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    public Optional<ParentCategoryDto> findParentCategory(String name) {
        String sql = "{call categories_pkg.find_parent_category(?, ?)}";
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cstmt = null;
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, name);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            ParentCategoryDto dto = null;
            if (rs.next()) {
                dto = new ParentCategoryDto(rs.getLong("category_id"),
                        rs.getString("category_name"));
            }
            return Optional.ofNullable(dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }
}
