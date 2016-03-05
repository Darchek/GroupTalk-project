package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.entity.Theme;
import edu.upc.eetac.dsa.groupTalk.entity.ThemeCollection;

import java.sql.SQLException;

/**
 * Created by Marti on 05/03/2016.
 */
public interface ThemeDAO {
    public Theme createTheme(String userid, String groupid, String title, String content) throws SQLException;

    public Theme getThemeById(String id) throws SQLException;

    public ThemeCollection getThemes() throws SQLException;

    public Theme updateTheme(String id, String title, String content) throws SQLException;

    public boolean deleteTheme(String id) throws SQLException;
}
