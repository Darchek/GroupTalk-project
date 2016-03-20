package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.entity.Comment;
import edu.upc.eetac.dsa.groupTalk.entity.Theme;
import edu.upc.eetac.dsa.groupTalk.entity.ThemeCollection;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marti on 05/03/2016.
 */
public interface ThemeDAO {

    public Theme createTheme(String userid, String groupid, String title, String content) throws SQLException;

    public Theme getThemeById(String id) throws SQLException;

    public ThemeCollection getThemesByGroupId(String groupid) throws SQLException;

    public Theme updateTheme(String id, String title, String content) throws SQLException;

    public boolean deleteTheme(String id) throws SQLException;
}
