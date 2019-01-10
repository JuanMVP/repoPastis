import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Tratamiento, { schema } from './model'

const router = new Router()
const { periodo_toma, duracion } = schema.tree

/**
 * @api {post} /tratamientos Create tratamiento
 * @apiName CreateTratamiento
 * @apiGroup Tratamiento
 * @apiParam periodo_toma Tratamiento's periodo_toma.
 * @apiParam duracion Tratamiento's duracion.
 * @apiSuccess {Object} tratamiento Tratamiento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Tratamiento not found.
 */
router.post('/',
  body({ periodo_toma, duracion }),
  create)

/**
 * @api {get} /tratamientos Retrieve tratamientos
 * @apiName RetrieveTratamientos
 * @apiGroup Tratamiento
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of tratamientos.
 * @apiSuccess {Object[]} rows List of tratamientos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /tratamientos/:id Retrieve tratamiento
 * @apiName RetrieveTratamiento
 * @apiGroup Tratamiento
 * @apiSuccess {Object} tratamiento Tratamiento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Tratamiento not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /tratamientos/:id Update tratamiento
 * @apiName UpdateTratamiento
 * @apiGroup Tratamiento
 * @apiParam periodo_toma Tratamiento's periodo_toma.
 * @apiParam duracion Tratamiento's duracion.
 * @apiSuccess {Object} tratamiento Tratamiento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Tratamiento not found.
 */
router.put('/:id',
  body({ periodo_toma, duracion }),
  update)

/**
 * @api {delete} /tratamientos/:id Delete tratamiento
 * @apiName DeleteTratamiento
 * @apiGroup Tratamiento
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Tratamiento not found.
 */
router.delete('/:id',
  destroy)

export default router
