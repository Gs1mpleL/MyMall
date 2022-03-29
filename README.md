# 项目进度
- 权限框架，剩下给登录用户授权，还有动态路径授权
- 商品：品牌接口，商品分类接口完成
- 管理员：只写了login接口，授权细节未完成



# 使用自定义注解实现参数校验
主要是实现ConstraintValidator<>接口


# 权限管理包 mall-security模块
## 登录权限管理
- 内置SpringSecurity
- 使用jwt来生成Token  荷载中存入username和过期时间 这些参数配置在配置文件中，通过注解@Value(${jwt.xxx})来获取
- 自定义配置文件来存储路径白名单 @ConfigurationProperties(prefix = "xxx")这样就可以把白名单写在配置文件中
- 创建SecurityConfig类继承WebSecurityConfigurerAdapter,其中就注入刚才配置好的白名单，从而达到配置白名单效果，然后做一些常规处理
- 定义好自定义的权限不足处理器和未登录处理器
- 添加一个前置过滤器，JwtAuthenticationTokenFilter.在校验密码过滤器前，获取Authorization请求头的内容
- 重写UserDetails的方法，绑定到login方法中，同时生成一个token，同时把token返回前端
- 至此完成权限控制的基础方案

## 资源访问权限管理
> 如果使用原始方法，需要在每个路径上添加@PreAutorize("hasAuthority('xxxx')") 难以维护

- 使用基于路径的动态权限控制
- 在项目启动时，创建一个Map<String,ConfigAttribute>来存储url对应的权限
- 实现一个路径拦截器，获取路径，根据Map获取对应的权限,交给权限校验Manager去决定
- 校验manager根据当前用户拥有的权限和路径需要的权限作比较，若包含，就放行



