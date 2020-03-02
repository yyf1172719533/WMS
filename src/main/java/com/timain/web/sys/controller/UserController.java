package com.timain.web.sys.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.pojo.Dept;
import com.timain.web.sys.pojo.Role;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.service.DeptService;
import com.timain.web.sys.service.RoleService;
import com.timain.web.sys.service.UserService;
import com.timain.web.sys.utils.PinYinUtils;
import com.timain.web.sys.utils.UADFileUtils;
import com.timain.web.sys.utils.UploadImgUtils;
import com.timain.web.sys.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 20:48
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有用户信息
     * @param userVO
     * @return
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVO userVO) {
        IPage<User> page = new Page<>(userVO.getPage(), userVO.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userVO.getName()), "loginName", userVO.getName()).or().eq(StringUtils.isNotBlank(userVO.getName()), "name", userVO.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVO.getAddress()), "address", userVO.getAddress());
        queryWrapper.eq("type", Constants.USER_TYPE_NORMAL);
        queryWrapper.eq(null!=userVO.getDeptId(), "deptId", userVO.getDeptId());
        this.userService.page(page, queryWrapper);

        List<User> userList = page.getRecords();
        for (User user: userList) {
            Integer deptId = user.getDeptId();
            if (null!=deptId) {
                Dept dept = deptService.getById(deptId);
                user.setDeptName(dept.getTitle());
            }
            Integer mgr = user.getMgr();
            if (null!=mgr) {
                User user1 = this.userService.getById(mgr);
                user.setLeaderName(user1.getName());
            }
        }
        return new DataGridView(page.getTotal(), userList);
    }

    /**
     * 加载最大排序码
     * @return
     */
    @RequestMapping("getOrderNum")
    public Map<String, Object> getOrderNum() {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("orderNum");
        IPage<User> page=new Page<>(1, 1);
        List<User> list = this.userService.page(page, queryWrapper).getRecords();
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrderNum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 根据部门ID查询本部门的领导
     * @param deptId
     * @return
     */
    @RequestMapping("loadMgrByDeptId")
    public DataGridView loadMgrByDeptId(Integer deptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(null!=deptId, "deptId", deptId);
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        queryWrapper.eq("type", Constants.USER_TYPE_NORMAL);
        List<User> userList = this.userService.list(queryWrapper);
        return new DataGridView(userList);
    }

    /**
     * 获取汉字的首字母
     * @param userName
     * @return
     */
    @RequestMapping("ChineseToPinYin")
    public Map<String, Object> ChineseToPinYin(String userName) {
        Map<String, Object> map = new HashMap<>();
        if (null!=userName) {
            map.put("value", PinYinUtils.getPinYin(userName));
        } else {
            map.put("value", "");
        }
        return map;
    }

    /**
     * 添加用户
     * @param userVO
     * @return
     */
    @RequestMapping("addUser")
    public ResultObj addUser(UserVO userVO) {
        try {
            userVO.setType(Constants.USER_TYPE_NORMAL);
            String salt = IdUtil.simpleUUID().toUpperCase();//设置盐
            userVO.setSalt(salt);
            userVO.setPwd(new Md5Hash(Constants.USER_DEFAULT_PWD, salt, 2).toString());//使用MD5加密
            this.userService.save(userVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 根据用户ID查询领导所在部门ID
     * @param id
     * @return
     */
    @RequestMapping("getUserById")
    public DataGridView getUserById(Integer id) {
        return new DataGridView(this.userService.getById(id));
    }

    /**
     * 修改用户
     * @param userVO
     * @return
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVO userVO) {
        try {
            this.userService.updateById(userVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id) {
        try {
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    @RequestMapping("resertPwd")
    public ResultObj resertPwd(Integer id) {
        try {
            User user = new User();
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();//设置盐
            user.setSalt(salt);
            user.setPwd(new Md5Hash(Constants.USER_DEFAULT_PWD, salt, 2).toString());//使用MD5加密
            this.userService.updateById(user);
            return ResultObj.RESERT_PWD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESERT_PWD_ERROR;
        }
    }

    /**
     * 根据用户ID查询用户所拥有的角色
     * @param id
     * @return
     */
    @RequestMapping("initRoleByUID")
    public DataGridView initRoleByUID(Integer id) {
        //查询所有可用的角色列表
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        List<Map<String, Object>> mapList = this.roleService.listMaps(queryWrapper);
        //查询当前用户所拥有的角色ID集合
        List<Integer> roleIds = this.roleService.findUserRoleIdsByUid(id);
        for (Map<String, Object> map: mapList) {
            Boolean LAY_CHECKED = false;
            Integer roleId = (Integer) map.get("id");//从map集合中根据用户ID拿出角色ID
            for (Integer rId: roleIds) {
                if (roleId==rId) {
                    LAY_CHECKED = true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(mapList.size()), mapList);
    }

    /**
     * 保存给用户分配的角色信息
     * @param uid
     * @param ids
     * @return
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid, Integer[] ids) {
        try {
            this.userService.saveUserRole(uid, ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * 修改个人资料
     * @param userVO
     * @return
     */
    @RequestMapping("updateUserInfo")
    public ResultObj updateUserInfo(UserVO userVO) {
        try {
            String newName = "";
            if (null!=userVO.getImgPath()) {
                if (userVO.getImgPath().endsWith("_temp")) {
                    newName = UploadImgUtils.changeFileName(userVO.getImgPath());
                    String oldPath = this.userService.getById(userVO.getId()).getImgPath();
                    UploadImgUtils.removeByPath(oldPath);
                }
            } else {
                newName = UploadImgUtils.changeFileName(userVO.getImgPath());
            }
            this.userService.updateById(userVO);
            User user = new User();
            user.setImgPath(newName);
            user.setId(userVO.getId());
            this.userService.updateById(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 上传图片
     * @param mf
     * @return
     * @throws IOException
     */
    @RequestMapping("uploadImg")
    public Map<String, Object> uploadImg(MultipartFile mf) throws IOException {
        //获取文件名
        String oldName = mf.getOriginalFilename();
        //创建新的文件名
        String stuff = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        String newName = IdUtil.simpleUUID().toUpperCase() + stuff;
        String dirName = "/resources/images";
        //构造文件夹
        File dirFile = new File(UploadImgUtils.UPLOAD_IMG_PATH, dirName);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //构造文件对象
        File file = new File(dirFile, newName + "_temp");
        //将mf中的图片存入file
        mf.transferTo(file);
        Map<String, Object> map = new HashMap<>();
        map.put("path", dirName + "/" + newName + "_temp");
        return map;
    }

    /**
     * 图片下载
     * @param path
     * @return
     */
    @RequestMapping("showImgByPath")
    public ResponseEntity<Object> showImgByPath(String path) {
        return UploadImgUtils.createResponseEntity(path);
    }

    /**
     * 修改用户密码
     * @param id
     * @param checkPwd
     * @return
     */
    @RequestMapping("updatePwd")
    public ResultObj updatePwd(Integer id, String checkPwd) {
        try {
            User user = new User();
            String salt = IdUtil.simpleUUID().toUpperCase();//设置盐
            user.setSalt(salt);
            user.setPwd(new Md5Hash(checkPwd, salt, 2).toString());//使用MD5加密
            user.setId(id);
            this.userService.updateById(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}
