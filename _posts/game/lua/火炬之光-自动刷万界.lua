function OnEvent(event, arg)
    OutputLogMessage("event = %s, arg = %s\n", event, arg)
    if(event == 'MOUSE_BUTTON_RELEASED' and arg==4 ) then
        -- 1.启动地图，移动进地图
        openMap()
        -- 2.等待地图加载
        Sleep(10*1000)
        -- 3.释放技能
        releaseSkill()
        -- 4.地图移动到BOSS
        attackBoss()
        -- 5.等待击杀BOSS
        Sleep(60*1000)
        -- 6.拾取物品
        pickup()
        -- 7.回城
        returnCity()
    end
end

--[[
   1.启动地图，移动进地图
]]
function openMap()
    OutputLogMessage("start open map \n ")
    -- 1.按D键
    PressAndReleaseKey("d")
    Sleep(500)
    -- 2.移动带地图
    MoveMouseTo(31828,39661)
    Sleep(500)
    -- 3.左键地图
    PressAndReleaseMouseButton(1)
    Sleep(500)

    -- 4.移动到确认
    MoveMouseTo(31931,60372)
    Sleep(500)
    -- 5.点击确认
    PressAndReleaseMouseButton(1)
    Sleep(1000)

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
   释放技能
]]
function releaseSkill()
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
地图移动到BOSS（万界版本）
]]
function attackBoss()
    MoveMouseTo(32989,8078)
    Sleep(500)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
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
       Sleep(500)
    end
end

--[[
回城
]]
function returnCity()
    PressKey('d')
    Sleep(100)
    ReleaseKey('d')
    Sleep(500)
end