

--[[
   1.启动地图，移动进地图
]]
function openMap()
    OutputLogMessage("start open map \n ")
    -- 1.按D键
    PressAndReleaseKey("d")
    Sleep(500)
    -- 2.选择雷鸣废土
    moveChoose(16631,15306)

    -- 3.移动凤鸣峡谷
    moveChoose(51465,32130)

    -- 4.点击下一步
    moveChoose(53412,56789)

    -- 5.确认地图
    moveChoose(53412,56789)

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
地图移动到BOSS（万界版本）
]]
function attackBoss()
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
    move(29233, 17249)
end