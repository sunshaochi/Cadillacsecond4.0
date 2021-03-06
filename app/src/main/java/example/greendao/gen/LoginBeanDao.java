package example.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import cadillac.example.com.cadillac.bean.LoginBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOGIN_BEAN".
*/
public class LoginBeanDao extends AbstractDao<LoginBean, Long> {

    public static final String TABLENAME = "LOGIN_BEAN";

    /**
     * Properties of entity LoginBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Id = new Property(1, int.class, "id", false, "ID");
        public final static Property UserName = new Property(2, String.class, "userName", false, "USER_NAME");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property RoleId = new Property(4, int.class, "roleId", false, "ROLE_ID");
        public final static Property RoleCode = new Property(5, String.class, "roleCode", false, "ROLE_CODE");
        public final static Property RoleName = new Property(6, String.class, "roleName", false, "ROLE_NAME");
        public final static Property MobileNo = new Property(7, String.class, "mobileNo", false, "MOBILE_NO");
        public final static Property DealerId = new Property(8, int.class, "dealerId", false, "DEALER_ID");
        public final static Property DealerCode = new Property(9, String.class, "dealerCode", false, "DEALER_CODE");
        public final static Property DealerFullCode = new Property(10, String.class, "dealerFullCode", false, "DEALER_FULL_CODE");
        public final static Property DealerType = new Property(11, String.class, "dealerType", false, "DEALER_TYPE");
        public final static Property DealerName = new Property(12, String.class, "dealerName", false, "DEALER_NAME");
        public final static Property GroupName = new Property(13, String.class, "groupName", false, "GROUP_NAME");
        public final static Property PersonName = new Property(14, String.class, "personName", false, "PERSON_NAME");
        public final static Property IsLogin = new Property(15, String.class, "isLogin", false, "IS_LOGIN");
        public final static Property IsLock = new Property(16, String.class, "isLock", false, "IS_LOCK");
        public final static Property LockTime = new Property(17, String.class, "lockTime", false, "LOCK_TIME");
        public final static Property LoginTime = new Property(18, String.class, "loginTime", false, "LOGIN_TIME");
        public final static Property LoginVerson = new Property(19, String.class, "loginVerson", false, "LOGIN_VERSON");
        public final static Property Admin = new Property(20, String.class, "admin", false, "ADMIN");
        public final static Property EditData = new Property(21, String.class, "editData", false, "EDIT_DATA");
        public final static Property IsDelete = new Property(22, String.class, "isDelete", false, "IS_DELETE");
        public final static Property DeleteTime = new Property(23, String.class, "deleteTime", false, "DELETE_TIME");
        public final static Property CreateTime = new Property(24, String.class, "createTime", false, "CREATE_TIME");
        public final static Property CreateUser = new Property(25, String.class, "createUser", false, "CREATE_USER");
        public final static Property LastUpdateTime = new Property(26, String.class, "lastUpdateTime", false, "LAST_UPDATE_TIME");
        public final static Property LastUpdateUser = new Property(27, String.class, "lastUpdateUser", false, "LAST_UPDATE_USER");
        public final static Property State = new Property(28, String.class, "state", false, "STATE");
    }


    public LoginBeanDao(DaoConfig config) {
        super(config);
    }
    
    public LoginBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOGIN_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: _id
                "\"ID\" INTEGER NOT NULL ," + // 1: id
                "\"USER_NAME\" TEXT," + // 2: userName
                "\"PASSWORD\" TEXT," + // 3: password
                "\"ROLE_ID\" INTEGER NOT NULL ," + // 4: roleId
                "\"ROLE_CODE\" TEXT," + // 5: roleCode
                "\"ROLE_NAME\" TEXT," + // 6: roleName
                "\"MOBILE_NO\" TEXT," + // 7: mobileNo
                "\"DEALER_ID\" INTEGER NOT NULL ," + // 8: dealerId
                "\"DEALER_CODE\" TEXT," + // 9: dealerCode
                "\"DEALER_FULL_CODE\" TEXT," + // 10: dealerFullCode
                "\"DEALER_TYPE\" TEXT," + // 11: dealerType
                "\"DEALER_NAME\" TEXT," + // 12: dealerName
                "\"GROUP_NAME\" TEXT," + // 13: groupName
                "\"PERSON_NAME\" TEXT," + // 14: personName
                "\"IS_LOGIN\" TEXT," + // 15: isLogin
                "\"IS_LOCK\" TEXT," + // 16: isLock
                "\"LOCK_TIME\" TEXT," + // 17: lockTime
                "\"LOGIN_TIME\" TEXT," + // 18: loginTime
                "\"LOGIN_VERSON\" TEXT," + // 19: loginVerson
                "\"ADMIN\" TEXT," + // 20: admin
                "\"EDIT_DATA\" TEXT," + // 21: editData
                "\"IS_DELETE\" TEXT," + // 22: isDelete
                "\"DELETE_TIME\" TEXT," + // 23: deleteTime
                "\"CREATE_TIME\" TEXT," + // 24: createTime
                "\"CREATE_USER\" TEXT," + // 25: createUser
                "\"LAST_UPDATE_TIME\" TEXT," + // 26: lastUpdateTime
                "\"LAST_UPDATE_USER\" TEXT," + // 27: lastUpdateUser
                "\"STATE\" TEXT);"); // 28: state
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOGIN_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LoginBean entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
        stmt.bindLong(2, entity.getId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindLong(5, entity.getRoleId());
 
        String roleCode = entity.getRoleCode();
        if (roleCode != null) {
            stmt.bindString(6, roleCode);
        }
 
        String roleName = entity.getRoleName();
        if (roleName != null) {
            stmt.bindString(7, roleName);
        }
 
        String mobileNo = entity.getMobileNo();
        if (mobileNo != null) {
            stmt.bindString(8, mobileNo);
        }
        stmt.bindLong(9, entity.getDealerId());
 
        String dealerCode = entity.getDealerCode();
        if (dealerCode != null) {
            stmt.bindString(10, dealerCode);
        }
 
        String dealerFullCode = entity.getDealerFullCode();
        if (dealerFullCode != null) {
            stmt.bindString(11, dealerFullCode);
        }
 
        String dealerType = entity.getDealerType();
        if (dealerType != null) {
            stmt.bindString(12, dealerType);
        }
 
        String dealerName = entity.getDealerName();
        if (dealerName != null) {
            stmt.bindString(13, dealerName);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(14, groupName);
        }
 
        String personName = entity.getPersonName();
        if (personName != null) {
            stmt.bindString(15, personName);
        }
 
        String isLogin = entity.getIsLogin();
        if (isLogin != null) {
            stmt.bindString(16, isLogin);
        }
 
        String isLock = entity.getIsLock();
        if (isLock != null) {
            stmt.bindString(17, isLock);
        }
 
        String lockTime = entity.getLockTime();
        if (lockTime != null) {
            stmt.bindString(18, lockTime);
        }
 
        String loginTime = entity.getLoginTime();
        if (loginTime != null) {
            stmt.bindString(19, loginTime);
        }
 
        String loginVerson = entity.getLoginVerson();
        if (loginVerson != null) {
            stmt.bindString(20, loginVerson);
        }
 
        String admin = entity.getAdmin();
        if (admin != null) {
            stmt.bindString(21, admin);
        }
 
        String editData = entity.getEditData();
        if (editData != null) {
            stmt.bindString(22, editData);
        }
 
        String isDelete = entity.getIsDelete();
        if (isDelete != null) {
            stmt.bindString(23, isDelete);
        }
 
        String deleteTime = entity.getDeleteTime();
        if (deleteTime != null) {
            stmt.bindString(24, deleteTime);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(25, createTime);
        }
 
        String createUser = entity.getCreateUser();
        if (createUser != null) {
            stmt.bindString(26, createUser);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(27, lastUpdateTime);
        }
 
        String lastUpdateUser = entity.getLastUpdateUser();
        if (lastUpdateUser != null) {
            stmt.bindString(28, lastUpdateUser);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(29, state);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LoginBean entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
        stmt.bindLong(2, entity.getId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindLong(5, entity.getRoleId());
 
        String roleCode = entity.getRoleCode();
        if (roleCode != null) {
            stmt.bindString(6, roleCode);
        }
 
        String roleName = entity.getRoleName();
        if (roleName != null) {
            stmt.bindString(7, roleName);
        }
 
        String mobileNo = entity.getMobileNo();
        if (mobileNo != null) {
            stmt.bindString(8, mobileNo);
        }
        stmt.bindLong(9, entity.getDealerId());
 
        String dealerCode = entity.getDealerCode();
        if (dealerCode != null) {
            stmt.bindString(10, dealerCode);
        }
 
        String dealerFullCode = entity.getDealerFullCode();
        if (dealerFullCode != null) {
            stmt.bindString(11, dealerFullCode);
        }
 
        String dealerType = entity.getDealerType();
        if (dealerType != null) {
            stmt.bindString(12, dealerType);
        }
 
        String dealerName = entity.getDealerName();
        if (dealerName != null) {
            stmt.bindString(13, dealerName);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(14, groupName);
        }
 
        String personName = entity.getPersonName();
        if (personName != null) {
            stmt.bindString(15, personName);
        }
 
        String isLogin = entity.getIsLogin();
        if (isLogin != null) {
            stmt.bindString(16, isLogin);
        }
 
        String isLock = entity.getIsLock();
        if (isLock != null) {
            stmt.bindString(17, isLock);
        }
 
        String lockTime = entity.getLockTime();
        if (lockTime != null) {
            stmt.bindString(18, lockTime);
        }
 
        String loginTime = entity.getLoginTime();
        if (loginTime != null) {
            stmt.bindString(19, loginTime);
        }
 
        String loginVerson = entity.getLoginVerson();
        if (loginVerson != null) {
            stmt.bindString(20, loginVerson);
        }
 
        String admin = entity.getAdmin();
        if (admin != null) {
            stmt.bindString(21, admin);
        }
 
        String editData = entity.getEditData();
        if (editData != null) {
            stmt.bindString(22, editData);
        }
 
        String isDelete = entity.getIsDelete();
        if (isDelete != null) {
            stmt.bindString(23, isDelete);
        }
 
        String deleteTime = entity.getDeleteTime();
        if (deleteTime != null) {
            stmt.bindString(24, deleteTime);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(25, createTime);
        }
 
        String createUser = entity.getCreateUser();
        if (createUser != null) {
            stmt.bindString(26, createUser);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(27, lastUpdateTime);
        }
 
        String lastUpdateUser = entity.getLastUpdateUser();
        if (lastUpdateUser != null) {
            stmt.bindString(28, lastUpdateUser);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(29, state);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LoginBean readEntity(Cursor cursor, int offset) {
        LoginBean entity = new LoginBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.getInt(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.getInt(offset + 4), // roleId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // roleCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // roleName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // mobileNo
            cursor.getInt(offset + 8), // dealerId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // dealerCode
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // dealerFullCode
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // dealerType
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // dealerName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // groupName
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // personName
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // isLogin
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // isLock
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // lockTime
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // loginTime
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // loginVerson
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // admin
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // editData
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // isDelete
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // deleteTime
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // createTime
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // createUser
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // lastUpdateTime
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // lastUpdateUser
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28) // state
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LoginBean entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.getInt(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRoleId(cursor.getInt(offset + 4));
        entity.setRoleCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRoleName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMobileNo(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDealerId(cursor.getInt(offset + 8));
        entity.setDealerCode(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDealerFullCode(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDealerType(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setDealerName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setGroupName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setPersonName(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setIsLogin(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setIsLock(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setLockTime(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setLoginTime(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setLoginVerson(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setAdmin(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setEditData(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setIsDelete(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setDeleteTime(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setCreateTime(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setCreateUser(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setLastUpdateTime(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setLastUpdateUser(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setState(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LoginBean entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LoginBean entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LoginBean entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
