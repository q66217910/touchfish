-- 循环刷图次数
CIRCULATE_COUNT=1
-- 回响个数
MAP_DIFFICULTY=1
-- 职业（1.召唤/2.炮宾）
CAREER=1
-- 地图（1.沸涌炎海-风鸣峡谷/100.沸涌炎海-监视者）
MAP=1
-- 移动速度
SPEED_MOVE=106

function OnEvent(event, arg)
    OutputLogMessage("event = %s, arg = %s\n", event, arg)
     if(event == 'MOUSE_BUTTON_RELEASED' and arg==5 ) then
                x, y = GetMousePosition();
                OutputLogMessage("Mouse is at %d, %d\n", x, y);
                AbortMacro();
     end
    if(event == 'MOUSE_BUTTON_RELEASED' and arg==4 ) then
       for i=1,CIRCULATE_COUNT,1
       do
       autoCarry(i)
       end
    end
end

function autoCarry(count)
	-- 1.启动地图，移动进地图
    openMap(2)
    -- 2.等待地图加载
    Sleep(5*1000)
    -- 3.释放技能
    releaseSkill()
    -- 4.地图移动到BOSS
    attackBoss()
    -- 6.拾取物品
    pickup()
    -- 7.回城
    returnCity()

    OutputLogMessage("end this \n ")
end

--[[
   释放技能
]]
function releaseSkill()
    if  (CAREER==1) then
        summoner()
    else if(CAREER==2) then
        bim()
      end
    end
end

--[[
  炮宾
]]
function bim()
  PressKey('r')
  Sleep(100)
  ReleaseKey('r')
  PressKey('w')
  Sleep(100)
  ReleaseKey('w')
  PressKey('q')
  Sleep(100)
  ReleaseKey('q')
end

--[[
 召唤技能(注意施法CD)
]]
function summoner()
    PressKey('q')
    Sleep(100)
    ReleaseKey('q')
    Sleep(500)

    PressKey('q')
    Sleep(100)
    ReleaseKey('q')
    Sleep(500)

    PressKey('q')
    Sleep(100)
    ReleaseKey('q')
    Sleep(500)

    PressKey('w')
    Sleep(100)
    ReleaseKey('w')
    Sleep(500)

    PressKey('w')
    Sleep(100)
    ReleaseKey('w')
    Sleep(500)

    PressKey('w')
    Sleep(100)
    ReleaseKey('w')
    Sleep(500)
end


--[[
拾取物品
]]
function pickup()
    for i=0,5,1
    do
       PressKey('a')
       Sleep(100)
       ReleaseKey('a')
    end
end

--[[
回城
]]
function returnCity()
    PressKey('t')
    Sleep(100)
    ReleaseKey('t')

    Sleep(5000)

    PressKey('d')
    Sleep(100)
    ReleaseKey('d')
    Sleep(500)

    Sleep(7000)

    OutputLogMessage("start next  \n ")

    move(45113, 18221)
    Sleep(500)
    move(45147, 18221)
end

--[[
移动
]]
function moveChoose(x,y)
    MoveMouseTo(x,y)
    Sleep(500)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
    Sleep(1000)
end

--[[
  攻击
]]
function attack()
  if(CAREER==1) then
    -- 召唤只需要移动就可以了
  else if (CAREER==2) then
         bim()
       end
  end
end

--[[
移动
]]
function move(x,y)
    attack()
    Sleep(200)
    MoveMouseTo(x,y)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
    Sleep(100)
    PressKey('a')
    Sleep(100)
    ReleaseKey('a')

    speedWait()
end

function speedWait()
	if(SPEED_MOVE>100) then
	 Sleep(500)
	 else if(SPEED_MOVE>50) then
	        Sleep(1000)
        end
    end
end

--[[
   1.启动地图，移动进地图
   mapState: 1：正常地图 2:Boss
]]
function openMap(mapState)
    OutputLogMessage("start open map \n ")
    -- 1.按D键
    PressAndReleaseKey("d")
    Sleep(500)

    -- 2.选择地图
   chooseMap()

    if (mapState==1) then
	    -- 3.点击下一步
        moveChoose(53412,56789)

        --4.加回想
        mapDifficulty()

        -- 5.确认地图
        moveChoose(53412,56789)
    elseif (mapState==2) then
        -- 确认boss
        moveChoose(33229, 60737)
    end


    OutputLogMessage("end open map \n ")

    -- 6.人物移动
    MoveMouseTo(37839,27696)
    Sleep(500)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
    Sleep(500)

    -- 7.进入地图
    PressAndReleaseKey("d")
end

--[[
  添加回响
]]
function mapDifficulty()
  for i=1,MAP_DIFFICULTY,1
  do
     moveChoose(8743,52355)
  end
end

--[[
 选择地图
]]
function chooseMap()
   if(MAP==1) then
      --沸涌炎海-风鸣峡谷
      fengMingCanyonOpen()
   else if(MAP==100) then
      --沸涌炎海-监视者
      feiYongYanHai()
       end
   end
end

--[[
    打开-沸涌炎海-监视者
]]
function feiYongYanHai()
    -- 1.选择沸涌炎海
    moveChoose(16631,15306)

    -- 2.选择监视者
    moveChoose(37122, 17371)
end

--[[
  打开-沸涌炎海-风鸣峡谷
]]
function fengMingCanyonOpen()
    -- 1.选择沸涌炎海
    moveChoose(16631,15306)

    -- 2.移动凤鸣峡谷
    moveChoose(51465,32130)
end

--[[
  沸涌炎海-风鸣峡谷
]]
function fengMingCanyonMove()
    move(42620, 20225)
    move(45045, 25084)
    move(44293, 25752)
    move(43918, 23505)
    move(43610, 24659)
    move(43132, 24477)
    move(41732, 20893)
    move(24315, 13787)
    move(26262, 22412)
    move(45318, 12937)
    move(46991, 22959)
    move(40503, 21622)
    move(34595, 21805)
    move(20729, 15245)
    move(26228, 22108)
    move(23905, 24052)
    move(20081, 29883)
    move(21071, 19618)
    move(20149, 31037)
    move(20149, 37110)
    move(23666, 51444)
    move(25989, 54663)
    move(19705, 50229)
    move(18032, 30125)
    move(16222, 35227)
    move(15504, 27878)
    move(19807, 22898)
    move(25579, 14091)
    move(38351, 17310)
    move(37497, 20347)
    move(39581, 17674)
    move(40981, 18889)
    move(40366, 18889)
    move(46035, 25752)
    move(49518, 31340)
    move(46581, 23687)
    move(42381, 20408)
    move(42722, 21136)
    move(42893, 19861)
    move(34629, 14273)
    move(35517, 17067)
    move(46957, 27878)
    move(43644, 22169)
    move(42961, 18100)
    move(43371, 19557)
    move(44737, 19679)
    move(46445, 19071)
    move(43644, 25024)
    move(46684, 22351)
    move(48665, 20347)
    move(44635, 22047)
    move(40195, 14698)
    move(28721, 14455)
    move(23325, 19011)
    move(18066, 20043)
    move(35380, 9475)
    move(30906, 9232)
    move(30872, 9353)
    move(24144, 14334)
    move(24520, 14152)
    move(35107, 12633)
    move(46889, 36867)
    move(44908, 47132)
    move(44464, 46707)
    move(49484, 32616)
    move(49587, 27149)
    move(45079, 19375)
    move(44771, 17796)
    move(45010, 12876)
    move(40537, 12937)
    move(44532, 14273)
    move(43849, 15913)
    move(42313, 17857)
    move(42313, 17857)
    move(42313, 17857)
    move(29233, 17249)
end

--[[
地图移动到BOSS（万界版本）
]]
function attackBoss()
    if(MAP==1) then
       fengMingCanyonMove()
    elseif (MAP==100) then

    end
end