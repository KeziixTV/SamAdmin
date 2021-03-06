package net.samagames.samadmin.questions;

import net.samagames.samadmin.SamAdmin;
import net.samagames.samadmin.common.AbstractCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This file is part of SamAdmin.
 *
 * SamAdmin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamAdmin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamAdmin.  If not, see <http://www.gnu.org/licenses/>.
 */
public class QuestionCache extends AbstractCache<Question>
{
    public QuestionCache(SamAdmin instance)
    {
        super(instance);
    }

    @Override
    public void processReload()
    {
        try (Connection sql = this.instance.getMySQLAgent().openApplicationConnection())
        {
            PreparedStatement pStatement = sql.prepareStatement("SELECT * FROM sam_questions");
            ResultSet result = pStatement.executeQuery();

            while(result.next())
            {
                Question question = new Question(result);
                this.cache.put(question.getID(), question);
            }

            sql.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
