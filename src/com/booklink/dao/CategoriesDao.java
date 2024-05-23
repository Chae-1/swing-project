package com.booklink.dao;

import com.booklink.model.categories.Categories;
import com.booklink.model.categories.CategoryDto;
import com.booklink.utils.DBConnectionUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDao {
    public void insertCategories(CategoryDto categoryDto) throws SQLException {
        String sql = "{call insert_Categories(?, ?)}";
        Connection con = null;

        try (Connection conn = DBConnectionUtils.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, categoryDto.name());
            stmt.setLong(2, categoryDto.priorId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCategories(Categories Categories) throws SQLException {
        String sql = "{call update_Categories(?, ?, ?)}";
    }

    public void deleteCategories(int CategoriesId) throws SQLException {
        String sql = "{call delete_Categories(?)}";

    }

    public List<Categories> getAllCategories() throws SQLException {
        List<Categories> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";

        return categories;
    }
}
