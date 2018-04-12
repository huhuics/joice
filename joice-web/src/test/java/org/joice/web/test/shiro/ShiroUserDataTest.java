/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.shiro;

import java.util.Set;

import javax.annotation.Resource;

import org.joice.mvc.dao.domain.SysPermissions;
import org.joice.mvc.dao.domain.SysRoles;
import org.joice.mvc.dao.domain.SysUsers;
import org.joice.mvc.shiro.service.PermissionService;
import org.joice.mvc.shiro.service.RoleService;
import org.joice.mvc.shiro.service.UserService;
import org.joice.web.test.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试用户权限数据
 * @author HuHui
 * @version $Id: ShiroUserDataTest.java, v 0.1 2018年4月12日 下午7:15:49 HuHui Exp $
 */
public class ShiroUserDataTest extends BaseTest {

    @Resource(name = "sysUserService")
    private UserService       userService;

    @Resource
    private RoleService       roleService;

    @Resource
    private PermissionService permissionService;

    @Test
    public void testInsertUsers() {
        SysUsers user = new SysUsers();
        user.setUsername("马六");
        user.setPassword("123");
        user.setSalt("123");
        user.setLocked(false);

        SysUsers createUser = userService.createUser(user);
        Assert.assertNotNull(createUser.getId());
    }

    @Test
    public void testInsertRoles() {
        SysRoles role = new SysRoles();
        role.setRole("PD");
        role.setDescription("产品经理");
        role.setAvailable(true);

        SysRoles createRole = roleService.createRole(role);
        Assert.assertNotNull(createRole.getId());
    }

    @Test
    public void testInsertPermission() {
        SysPermissions permission = new SysPermissions();
        permission.setPermission("select");
        permission.setDescription("查询");
        permission.setAvailable(true);
        permissionService.createPermission(permission);
    }

    @Test
    public void testInsertRolesPermissions() {
        roleService.correlationPermissions(1L, 3L, 4L);
        roleService.correlationPermissions(2L, 1L, 2L, 4L);
        roleService.correlationPermissions(3L, 4L);
    }

    @Test
    public void testInsertUsersRoles() {
        userService.correlationRoles(1L, 1L);
        userService.correlationRoles(2L, 2L);
        userService.correlationRoles(3L, 3L);
        userService.correlationRoles(4L, 2L, 3L);
    }

    @Test
    public void testFindRoles() {
        Set<SysRoles> roles1 = userService.findRoles(1L);
        Assert.assertTrue(roles1.size() == 1);

        Set<SysRoles> roles2 = userService.findRoles(4L);
        Assert.assertTrue(roles2.size() == 2);
    }

    @Test
    public void testFindPermissions() {
        Set<SysPermissions> permissions1 = userService.findPermissions(1L);
        Assert.assertTrue(permissions1.size() == 2);

        Set<SysPermissions> permissions2 = userService.findPermissions(2L);
        Assert.assertTrue(permissions2.size() == 3);

        Set<SysPermissions> permissions3 = userService.findPermissions(4L);
        Assert.assertTrue(permissions3.size() == 3);
    }

}
