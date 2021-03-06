# 使用自定义注解实现参数校验
@Constraint(validatedBy = FlagValidatorClass.class)，在自定义注解中加入这个注解指定谁来进行校验
主要是实现ConstraintValidator<xxx,xxx>接口第一个需要指定为你自定义的校验注解类，第二个指定为你要校验属性的类型


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

# 切面
- 对所有的Controller做切面，可以用来记录日志
- 对所有缓存操作做切面，可以对所有缓存做异常处理，如果没有开Redis，就不会出现错
- 对controller也可以做异常处理，但是有更方便的方法是创建异常处理类加注解@ControllerAdvice

# 商品模块
## pms_product
整体上看，商品表包含了商品的状态（是否逻辑删除，是否展示，是否促销，是否审核等等），商品分类（品牌分类，类型分类），商品库存，商品价格等等
- id
- 品牌id
- 商品分类id
- 运费模板???? 什么东西
- 商品属性分类id
- 商品名称
- 图片
- 货号
- 删除状态：0->未删除；1->已删除
- 上架状态：0->下架；1->上架
- 新品状态:0->不是新品；1->新品
- 推荐状态；0->不推荐；1->推荐
- 审核状态：0->未审核；1->审核通过
- 排序 ？？？ 这是？
- 销量
- 价格
- 促销价格
- 赠送的成长值
- 赠送的积分
- 限制使用的积分数
- 副标题
- 商品描述
- 市场价
- 库存
- 库存预警
- 单位
- 商品重量，默认为克
- 是否为预告商品：0->不是；1->是
- 以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮
- 关键字
- 备注
- 画册图片，连产品图片限制为5张，以逗号分割
- 详情标题
- 详情描述
- 产品详情网页内容
- 移动端网页详情
- 促销开始时间
- 促销结束时间
- 活动限购数量
- 促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购
- 产品分类名称
- 品牌名称

## pms_product_attribute
商品属性表，很多的属性，和他的选择状态，便于前端设计是复选框，还是是否选择类型
通过商品属性类型表，总结一类商品需要有的属性，赋给商品的类型表，就可以说明这一类产品都需要有这些属性

## pms_product_full_reduction
- 商品id
- 商品满足金额
- 商品减少金额

## pms_product_ladder
多买就会减免
- 商品id
- 满足减免的商品数量
- 折扣
- 折扣后的价格

## pms_sku_stock
SKU表，SKU表示的是一个具体的产品，产品名+各种属性参数
- 商品id
- sku编码
- 价格
- 库存
- 预警库存
- 属性信息
- 销量
- 单品促销价格
- 锁定库存     -> 这个东西之后可能会用到！！！！！


## 订单管理
### oms_order
一个订单包含给用户的大部分信息
- 订单id
- 会员id
- 优惠券id
- 订单编号
- 提交时间
- 用户账号
- 订单总金额
- 实际金额
- 运费金额
- 促销优化金额
- 积分折扣金额
- 积分抵扣金额
- 优惠卷抵扣金额
- 管理员后端调整金额
- 支付方式：0->未支付；1->支付宝；2->微信
- 订单来源：0->PC订单；1->app订单
- 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
- 订单类型：0->正常订单；1->秒杀订单
- 物流公司(配送方式)
- 物流单号
- 自动确认时间（天）
- 可以获得的积分
- 可以活动的成长值
- 活动信息
- 发票类型：0->不开发票；1->电子发票；2->纸质发票
- 发票抬头
- 发票内容
- 收票人电话
- 收票人邮箱
- 收货人姓名
- 收货人电话
- 收货人邮编
- 详细地址
- 确认收货状态：0->未确认；1->已确认
- 删除状态：0->未删除；1->已删除
- 下单时使用的积分
- 支付时间
- 发货时间
- 确认收货时间
- 评价时间
- 修改时间

### oms_order_item
一个订单中不一定只有一个商品信息，这个表存储每个商品具体信息
其中就包括SKU，因为已经具体到一个带属性的商品了

### oms_order_operate_history
当对订单操作时，需要在这里记录


### 对于用户来说，有一个购物车表
将商品和用户关联

可以根据购物车信息来生成一个订单，对于订单管理是交给后台来做

## 营销管理

### sms_flash_promotion
限时购表
create table sms_flash_promotion
(
id                   bigint not null auto_increment,
title                varchar(200) comment '标题',
start_date           date comment '开始日期',
end_date             date comment '结束日期',
status               int(1) comment '上下线状态',
create_time          datetime comment '创建时间',
primary key (id)
);

### sms_flash_promotion_session
限购场次表
create table sms_flash_promotion_session
(
id                   bigint not null auto_increment comment '编号',
name                 varchar(200) comment '场次名称',
start_time           time comment '每日开始时间',
end_time             time comment '每日结束时间',
status               int(1) comment '启用状态：0->不启用；1->启用',
create_time          datetime comment '创建时间',
primary key (id)
);

### 优惠卷
sms_coupon：存储每种优惠卷的信息

sms_coupon_history： 存储优惠卷使用与会员id关联，0，1区分会员是否使用了优惠券

sms_coupon_product_relation： 优惠卷和商品关联

sms_coupon_product_category_relation：优惠卷与商品类型关联，通过优惠卷表的字段use_type来区分到底与商品关联还是与商品类型关联